# Spring Kafka Auto Commit & AckMode 정리

Kafka를 Spring에서 사용할 때, 메시지 유실 방지를 위해 `auto commit`과 `AckMode`를 올바르게 이해하는 것이 중요합니다.

---

## Kafka Auto Commit 기본 동작

| 설정 항목                 | 기본값 | 설명 |
|--------------------------|--------|------|
| `enable.auto.commit`     | `true` | KafkaConsumer가 주기적으로 오프셋을 자동 커밋 |
| `auto.commit.interval.ms`| `5000` | poll() 호출 후 5초마다 커밋 수행 |

- `poll()`은 여러 메시지를 배치로 가져옵니다.
- 자동 커밋은 메시지 **처리 성공 여부와 무관하게** 커밋됨 → 유실 가능성 있음

---

## Spring Kafka의 AckMode 종류

`enable.auto.commit=false`일 때, Spring Kafka는 아래 AckMode로 오프셋 커밋 타이밍을 제어할 수 있습니다.

| AckMode             | 설명 |
|---------------------|------|
| `RECORD` (기본값)   | 메시지 한 건 처리 후 자동 커밋 |
| `BATCH`             | poll()으로 받은 배치 전체 처리 후 커밋 |
| `TIME`              | 지정된 시간마다 커밋 |
| `COUNT`             | 지정된 건수마다 커밋 |
| `COUNT_TIME`        | 시간 또는 건수 중 하나 만족 시 커밋 |
| `MANUAL`            | 사용자가 `ack.acknowledge()`로 직접 커밋 |
| `MANUAL_IMMEDIATE`  | `ack.acknowledge()` 호출 즉시 커밋 (바로 실행됨) |

---

## 사용 예시

### RECORD (기본값)
```java
@KafkaListener(topics = "topic", ackMode = "RECORD")
public void consume(String msg) {
    // 처리 후 자동 커밋
}
```

### MANUAL
```java
@KafkaListener(topics = "topic", ackMode = "MANUAL")
public void consume(String msg, Acknowledgment ack) {
    process(msg);
    ack.acknowledge(); // 수동 커밋
}
```

### BATCH
```java
@KafkaListener(topics = "topic", containerFactory = "batchFactory", ackMode = "BATCH")
public void consume(List<String> msgs) {
    for (String msg : msgs) {
        process(msg);
    }
    // 자동 커밋 (배치 처리 후)
}
```

---

## ✅ 실무 권장 조합

| 목적                        | 설정 |
|-----------------------------|------|
| 빠른 처리 + 유실 허용       | `enable.auto.commit=true`, `AckMode.RECORD` |
| 유실 방지 (정확한 처리)     | `enable.auto.commit=false`, `AckMode.MANUAL` |
| 대용량 배치 처리 최적화     | `enable.auto.commit=false`, `AckMode.BATCH` |
| 예외처리, 재시도 정교화     | `AckMode.MANUAL_IMMEDIATE` + DLQ/Retry 처리 |

---

## ✅ 핵심 요약

- `poll()`은 항상 배치로 메시지를 가져옴
- 단건 처리 시 자동 커밋은 유실 위험 존재
- **실무에서는 수동 커밋 + AckMode 설정이 핵심**
