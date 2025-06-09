# Kafka 리텐션(Retention) 정책 정리

Kafka의 **리텐션 정책**은 메시지를 브로커에 얼마나 오래 저장할지를 제어합니다.

---

## 개요

- Kafka는 메시지를 **컨슈머가 읽었는지 여부와 관계없이** 삭제할 수 있음
- 리텐션 정책은 **시간 또는 용량 기준**으로 메시지를 유지

---

## 리텐션 기준

| 기준       | 설정 키         | 설명 |
|------------|------------------|------|
| 시간 기준  | `retention.ms`   | 해당 시간(ms)이 지나면 메시지 삭제 |
| 용량 기준  | `retention.bytes`| 파티션 크기가 초과하면 오래된 메시지부터 삭제 |

※ 둘 중 먼저 도달한 조건이 우선 적용됨

---

## 🔧 설정 예시

### 토픽 생성 시

```bash
--config retention.ms=604800000         # 7일
--config retention.bytes=1073741824     # 1GB
```

### 기존 토픽 변경

```bash
kafka-configs.sh --bootstrap-server localhost:9092 \\
  --entity-type topics --entity-name my-topic \\
  --alter --add-config retention.ms=86400000
```

---

## 삭제 정책 (cleanup.policy)

Kafka는 메시지를 **삭제(delete)** 또는 **압축(compact)** 방식으로 리텐션을 적용할 수 있음.

| 설정 값 | 의미 |
|---------|------|
| `delete` | 시간/용량 초과 시 메시지를 물리적으로 삭제 (기본값) |
| `compact` | 같은 key의 최신 메시지만 유지 (이전 값들은 제거) |
| `compact,delete` | 두 방식을 함께 사용 |

```bash
--config cleanup.policy=delete
--config cleanup.policy=compact
--config cleanup.policy=compact,delete
```

---

## 유의 사항

- Kafka는 메시지를 **컨슈머가 읽지 않아도 삭제**할 수 있음
- 컨슈머가 너무 늦게 poll()을 하면 **`OffsetOutOfRangeException`** 발생 가능

---

## 요약

| 항목 | 설명 |
|------|------|
| `retention.ms` | 메시지 저장 시간 (기본: 7일) |
| `retention.bytes` | 파티션 최대 용량 초과 시 삭제 |
| `cleanup.policy` | 메시지 유지 방식: `delete`, `compact`, or both |
| 메시지 삭제 시점 | 시간 초과 or 용량 초과 |
| 컨슈머 주의사항 | 너무 늦게 읽으면 메시지가 삭제되어 유실 가능 |