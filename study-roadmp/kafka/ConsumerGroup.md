# Kafka Consumer Group & Rebalancing

## Kafka Consumer Group 기본 개념

- 하나의 Consumer Group은 여러 Consumer 인스턴스로 구성됩니다.
- 각 파티션은 하나의 Consumer에만 할당됨.
- 여러 Consumer Group이 하나의 Topic을 구독할 수 있음 (브로드캐스트 방식으로 전달 가능).
- Consumer Group은 **오프셋(Offset)** 정보를 저장하며 메시지 소비 위치를 관리함.

---

## Kafka 리밸런싱(Rebalancing)

### 리밸런싱이란?
- 파티션 ↔ 컨슈머 간 매핑을 다시 하는 작업
- 다음 경우에 발생:
  - 컨슈머가 추가/제거될 때
  - 컨슈머가 죽거나 응답하지 않을 때
  - 파티션 수가 변경될 때

### 리밸런싱의 부작용
- 리밸런싱 중에는 컨슈머가 메시지를 소비하지 못함 (서비스 일시 정지)
- **오프셋 커밋 타이밍에 따라 중복 소비 발생 가능**

---

## 오프셋 커밋 실패 시 중복 소비

### 예시 상황

1. Consumer A가 offset=100 메시지를 가져와 처리 중
2. 커밋 직전에 리밸런싱이 발생 → Consumer A 중단
3. Kafka는 마지막 커밋된 offset=99만 기억
4. Consumer B가 offset=100부터 다시 읽음 → 중복 처리 발생

---

## 수동 커밋(MANUAL)도 안전하지 않을 수 있음

- 수동 커밋(`ack.acknowledge()`)을 사용해도 리밸런싱 전에 커밋이 되지 않으면 똑같은 문제 발생
- Kafka의 기본 보장 방식: **At-least-once (최소 1번)**

---

## 실무 대응 전략

| 전략 | 설명 |
|------|------|
| 멱등한 처리 로직 | 동일 메시지 중복 처리에 안전하게 설계 (`UPSERT`, `ON CONFLICT DO NOTHING` 등) |
| 리밸런싱 최소화 | `max.poll.interval.ms`, `session.timeout.ms`, `heartbeat.interval.ms` 조정 |
| 파티션 수 관리 | 파티션 수보다 많은 Consumer를 할당하지 않음 |
| 커밋 전략 조정 | BATCH + MANUAL_IMMEDIATE 사용으로 안정된 커밋 시점 확보 |

---

## Spring Kafka에서의 관련 설정

- `@KafkaListener(concurrency = N)`으로 Consumer 인스턴스 개수 조절
- `AckMode` 종류
  - `RECORD`: 메시지 하나 처리 후 커밋
  - `BATCH`: 배치 단위로 커밋
  - `MANUAL_IMMEDIATE`: 명시적으로 커밋
- `enable.auto.commit=false` + 수동 커밋 설정 권장
- 파티션 수보다 concurrency × 서버 수가 크면 일부 컨슈머는 파티션 할당 못 받음

---

## Spring Kafka의 `concurrency` 설정

### `concurrency`란?

- `@KafkaListener(concurrency = N)` 옵션을 통해 **하나의 리스너 컨테이너가 내부적으로 몇 개의 스레드(컨슈머)를 생성할지** 결정.
- 이 컨슈머들은 같은 그룹 ID를 사용하므로 **컨슈머 그룹 내 병렬 소비**가 가능함.

---

### 주의사항

| 조건 | 설명 |
|------|------|
| `concurrency` > 파티션 수 | 일부 컨슈머는 할당받지 못하고 **idle 상태**가 됨 |
| `concurrency × 서버 수 > 파티션 수` | 전체적으로 과도한 컨슈머 생성 → 리밸런싱 잦아지고 **낭비 증가** |
| 동일 key로 들어온 메시지를 다수 컨슈머가 처리 | **메시지 순서 보장 깨짐** 가능성 있음 (멀티 스레드 주의) |

---

### Best Practice

- **파티션 수 = 총 컨슈머 수 (concurrency × 서버 수)** 로 맞추는 것이 가장 효율적
- 동일 key 메시지 순서를 지켜야 한다면 `concurrency = 1` 또는 파티션을 key 기준으로 잘 나누기
- 병렬성이 꼭 필요하면 내부 처리 로직은 **순서 독립적이고 멱등하게 작성**

## 핵심 요약

- Kafka는 "최소 1번" 메시지 전달을 보장
- 커밋 실패 시 메시지 중복 소비 가능
- 커밋 시점보다 리밸런싱이 먼저 발생하면 **수동 커밋도 무용지물**
- 최종 방어선은 **처리 로직의 멱등성**
"""