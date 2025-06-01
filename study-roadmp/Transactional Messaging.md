# Transactional Messaging

Transactional Messaging은 데이터베이스 트랜잭션과 메시지 발송(예: Kafka, RabbitMQ)을 **원자적으로 처리**하려는 아키텍처 패턴입니다.  
즉, **DB 저장과 메시지 발행을 모두 성공하거나 모두 실패**하게 만들기 위한 전략입니다.

---

## 왜 필요한가?

마이크로서비스 환경에서 이벤트 기반으로 서비스가 동작할 때 다음과 같은 문제가 발생할 수 있습니다:

| 상황 | 결과 |
|------|------|
| DB 저장 성공, Kafka 발송 실패 | 이벤트 유실 (소비자에게 정보 전달 안 됨) |
| DB 저장 실패, Kafka 발송 성공 | **유령 이벤트** 발생 (실제 존재하지 않는 데이터에 대한 이벤트 발행) |

이러한 **불일치**를 막기 위해 Transactional Messaging이 필요합니다.

---

## 구현 방식

### 1. 직접 트랜잭션 (비추천)

```java
@Transactional
public void doSomething() {
    repository.save(data);
    kafka.send(event); // 실패하면 rollback 불가
}
```

- DB와 Kafka 간 **원자성 보장 불가능**
- Kafka가 실패해도 DB는 커밋될 수 있음

---

### 2. Outbox Pattern 기반 구현 (권장)

#### 흐름
1. 서비스 로직에서 도메인 데이터와 Outbox 테이블에 이벤트를 같이 저장
2. 트랜잭션 커밋
3. 별도 퍼블리셔가 Outbox 테이블을 읽어 Kafka로 발행
4. 성공 시 상태를 SENT로 변경

#### 예시 Outbox 테이블 구조

| 컬럼명       | 설명                  |
|--------------|-----------------------|
| id           | 고유 식별자            |
| payload      | 발행할 메시지 내용     |
| status       | `PENDING`, `SENT` 등 |
| created_at   | 생성 시간             |

---

## 관련 패턴

| 패턴 | 설명 |
|------|------|
| Outbox Pattern | DB에 먼저 메시지를 저장 후 발행하는 전략 |
| Transactional Outbox + Polling Publisher | 트랜잭션 보장 + Kafka로 비동기 발행 |
| Transactional Outbox + Debezium CDC | DB 변경 감지 도구로 Kafka 자동 전송 |
| 2PC (Two Phase Commit) | 분산 트랜잭션 기반 원자성 처리 (복잡하고 잘 안 씀) |

---

## 요약

| 항목 | 설명 |
|------|------|
| 목적 | DB 저장과 메시지 발행의 원자성 보장 |
| 위험 | 하나만 성공하거나 하나만 실패할 경우 불일치 발생 |
| 해결 | Outbox Pattern 기반의 Transactional Messaging |
| 장점 | 신뢰성, 재처리 가능, 유실 방지 |
| 단점 | Outbox 테이블 관리 필요, 배치 퍼블리셔 구성 필요 |
