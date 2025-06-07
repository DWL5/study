#  Kafka 메시지 유실 방지 전략

Kafka에서 메시지 유실은 다음 3단계에서 발생할 수 있습니다:

- ① 프로듀서  브로커 전송
- ② 브로커 저장 및 복제
- ③ 컨슈머 오프셋 커밋

---

## 1 프로듀서 측: 안전하게 전송하기

- `acks=all`  
   리더 + ISR(복제본) 모두 쓰기 완료 시 성공 응답

- `retries > 0`  
   일시적 장애 시 재시도

- `enable.idempotence=true`  
   중복 전송 방지 (Kafka 0.11+)

- `linger.ms`, `batch.size`  
   적절한 배치 설정으로 안정성 + 성능 확보

** 추천 설정:**

```properties
acks=all
retries=5
enable.idempotence=true
```

---

## 2 브로커 측: 복제/리더 선출 안정화

- `min.insync.replicas=2`  
   쓰기 시 최소 2개 복제본 동기화 필요

- `replication.factor=3`  
   복제본 3개 이상 권장

- `unclean.leader.election.enable=false`  
   손상된 복제본이 리더로 승격되는 상황 방지

---

## 3 컨슈머 측: 메시지를 제대로 커밋하기

- `enable.auto.commit=false`  
   수동 커밋 사용

- 수동 커밋 (`ack.acknowledge()` or `commitSync()`)

- 실패 시 재시도 or DLQ 사용

** Spring Kafka 예시:**

```java
@KafkaListener(topics = "topic", ackMode = "MANUAL")
public void consume(String msg, Acknowledgment ack) {
    try {
        process(msg); // 메시지 처리
        ack.acknowledge(); // 커밋
    } catch (Exception e) {
        // 재시도 또는 DLQ 전송
    }
}
```

---

## 4 운영 측면 체크리스트

- **모니터링**: Consumer lag, commit 상태, 브로커 상태
- **DLQ/Retry**: 실패 메시지 보관 및 재처리 전략
- **트랜잭션 사용**: `initTransactions() + sendOffsetsToTransaction()` 조합으로 Producer + Consumer 원자적 처리

---

##  요약

| 구간 | 전략 |
|------|------|
| Producer | acks=all, retries > 0, enable.idempotence |
| Broker   | min.insync.replicas  2, replication.factor  3 |
| Consumer | enable.auto.commit=false, 수동 커밋, DLQ 처리 |
| 운영     | 트랜잭션, 모니터링, 장애 대응 체계 구축 |
