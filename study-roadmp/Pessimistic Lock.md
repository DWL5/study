# 비관적 락 (Pessimistic Lock)

## 개요

비관적 락(Pessimistic Lock)은  
**동시성 문제를 예방하기 위해 데이터를 읽거나 수정하기 전에 락을 거는 전략**이다.

"다른 트랜잭션이 이 데이터를 수정할 가능성이 있다"는 전제하에,  
**락을 먼저 걸어 충돌 자체를 차단**한다.

---

## 주요 특징

- 데이터를 **조회할 때(`SELECT ... FOR UPDATE`) 또는 수정할 때(`UPDATE`)**, 해당 행에 **쓰기 락(X-Lock, exclusive lock)**이 걸림
- 락이 유지되는 동안 **다른 트랜잭션은 해당 행을 수정하거나 삭제할 수 없음**
- 주로 **재고 차감, 계좌 이체, 주문 처리 등 정합성이 중요한 로직**에 사용

---

## 사용 예시 (SQL)

### 1. SELECT 시점에 락
```sql
START TRANSACTION;

SELECT * FROM product WHERE id = 1 FOR UPDATE;

-- 트랜잭션이 끝날 때까지 id = 1에 다른 트랜잭션의 접근 차단

COMMIT;
```

### 2. UPDATE 시점에 락 (자동 적용)
```sql
START TRANSACTION;

UPDATE product SET stock = stock - 1 WHERE id = 1;

-- 이 시점에 자동으로 배타적 락이 걸림

COMMIT;
```

> `UPDATE` 구문 자체도 자동으로 **비관적 락을 건다** (명시할 필요 없음)

---

## JPA에서 사용 예시

### 1. 명시적 비관적 락
```java
Member member = em.find(Member.class, 1L, LockModeType.PESSIMISTIC_WRITE);
```

- 실행 SQL 예시:
  ```sql
  SELECT * FROM member WHERE id = 1 FOR UPDATE;
  ```

### 2. 기본 `UPDATE` 시 자동 락
```java
member.setAge(30); // dirty checking

em.flush(); // 내부적으로 UPDATE 쿼리 → 락 자동 적용
```

---

## 장점

- **충돌 방지 확실**
- 데이터 정합성 강력 보장

---

## 단점

- **동시성 낮음**
- 락이 오래 유지되면 **데드락 위험**
- 전체 성능 저하 가능

---

## 언제 사용할까?

| 상황 | 설명 |
|------|------|
| 충돌 가능성이 높을 때 | 동시에 같은 데이터를 수정할 수 있음 |
| 반드시 정합성이 보장되어야 할 때 | 예: 재고, 포인트, 계좌 등 |
| 낙관적 락으로는 감당이 안 될 때 | 트랜잭션 재시도 대신 아예 막는 전략 사용 |

---

## 요약

> 비관적 락은 **데이터 충돌을 사전에 막기 위해 조회 또는 수정 시점에 락을 거는 방식**이며,  
> **동시성보다 정합성이 중요한 상황**에 적합하다.
