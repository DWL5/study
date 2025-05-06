# 커버링 인덱스 전략


## Clustered Index vs Secondary Index

| 항목 | Clustered Index (클러스터 인덱스) | Secondary Index (보조 인덱스) |
|------|-----------------------------------|-------------------------------|
| 정의 | 실제 **데이터의 물리적 정렬 순서**를 결정하는 인덱스 | 데이터와 별개로 저장되는 **보조 인덱스** |
| 저장 구조 | 인덱스의 리프 노드에 **데이터 row 전체 저장** | 리프 노드에 **Primary Key 값만 저장** |
| 개수 | 테이블당 **1개만 존재 (보통 PK)** | **여러 개 생성 가능** |
| 성능 | 범위 조회, 정렬에 유리 | 조건 검색에 유리하지만 **PK 조회 추가 발생** |
| INSERT/UPDATE 비용 | **높음** (정렬 유지 필요) | **낮음**, 데이터 변경 영향 적음 |

- **Clustered Index**  
  → 인덱스 트리의 리프 노드에 실제 데이터 row가 저장됨

- **Secondary Index**
  → 리프 노드에는 인덱스 컬럼 데이터와 **PK 값만** 저장됨 → 실제 데이터 조회 시 **PK로 다시 테이블 검색**


- Primary Key를 이용한 데이터 조회는 Clustered Index를 통해 데이터를 빠르게 찾을 수 있음
- SecondaryIndex는 데이터에 접근하기 위한 포인터(PK)만 가지고있다. 데이터는 Clustered Index가 가지고 있다.
- SecondaryIndex를 위한 데이터 조회는 인덱스 트리를 두번 타고 있다.
  - Secondary Index에서 데이터에 접근하기 위한 포인터를 찾은 뒤,
  - Clustered Index에서 데이터를 찾는다.

---

## 일반적인 페이지네이션 쿼리

```sql
SELECT * FROM article
WHERE board_id = 1
ORDER BY article_id DESC
LIMIT 30 OFFSET 1499970;
```

### ❗ 문제점

- `OFFSET`이 커질수록 성능이 급격히 저하됨
- MySQL은 `OFFSET` 수만큼 row를 **읽고 버림**
- 예: 1499970페이지 조회 시 → **1499970개 row를 읽고 버리고** 마지막 10개만 전달

1. (board_id, article_id)에 생성된 Secondary Index에서 article_id를 찾는다.
2. Clusted Index에서 article 데이터를 찾는다.
3. offset 1499970을 만날 때까지 반복하며 skip한다.
4. limit 30개를 추출한다.

---

## 커버링 인덱스 (Covering Index)

### 커버링 인덱스란?

- **SELECT 대상 컬럼들이 모두 인덱스에 포함된 경우**, MySQL은 **데이터 파일을 읽지 않고 인덱스만으로 쿼리 수행** 가능
- 즉, **디스크 I/O 감소 → 성능 향상**

---

## 커버링 인덱스 적용 예

```sql
CREATE INDEX idx_board_id_article_id 
ON article(board_id ASC, article_id DESC);
```

- 위에 인덱스는 기존에 적용되어있다.
- 커버링 인덱스가 적용된 서브쿼리를 만들고 이를 article 테이블과 join하여 얻고 싶은 30개의 데이터만 얻을 수 있다.

```sql
SELECT *
FROM (SELECT article_id FROM article
  WHERE board_id = 1
  ORDER BY article_id DESC
  LIMIT 30 OFFSET 1499970) t
LEFT JOIN article ON t.article_id = article.article_id;
```

### ✅ 장점

- 테이블 접근 없이 인덱스에서 모든 데이터 해결 가능
- `EXPLAIN`에서 `Extra: Using index` 로 표시됨 → 커버링 인덱스 적용 완료

---

## 5. `EXPLAIN` 결과 예시

```sql
EXPLAIN SELECT *
FROM (SELECT article_id
  FROM article
  WHERE board_id = 1
  ORDER BY article_id DESC
  LIMIT 30 OFFSET 1499970) t
LEFT JOIN article ON t.article_id = article.article_id;

```

| id | select_type | table      | partitions | type   | possible_keys           | key                     | key_len | ref          | rows    | filtered | Extra        |
|----|-------------|------------|------------|--------|-------------------------|-------------------------|---------|--------------|---------|----------|--------------|
| 1  | PRIMARY     | <derived2> | NULL       | ALL    | NULL                    | NULL                    | NULL    | NULL         | 1500000 | 100.00   | NULL         |
| 1  | PRIMARY     | article    | NULL       | eq_ref | PRIMARY                 | PRIMARY                 | 8       | t.article_id | 1       | 100.00   | NULL         |
| 2  | DERIVED     | article    | NULL       | ref    | idx_board_id_article_id | idx_board_id_article_id | 8       | const        | 3996049 | 100.00   | Using index  |

---

### ❗그러나 커버링인덱스가 적용되어도 offset이 한없이 커지면 느려짐
- OFFSET이 클수록 **건너뛰어야 할 인덱스 항목이 많아짐**
- 이 과정은 내부적으로 `O(n)` 반복 수행되므로 → **인덱스 사용 여부와 관계없이 느려짐**

## 6. 정리

| 방식 | 장점 | 단점 |
|------|------|------|
| OFFSET 방식 | 구현 간단 | 페이지가 깊어질수록 성능 저하 |
| 기준점 방식(커서 방식) | 성능 일정, 빠름 | 마지막 article_id를 매번 기억해야 함 |
| 커버링 인덱스 | 디스크 접근 없이 빠름 | 인덱스 크기 증가, 관리 복잡도 증가 |

---

## 참고 강의

[스프링부트로 대규모 시스템 설계](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8%EB%A1%9C-%EB%8C%80%EA%B7%9C%EB%AA%A8-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%84%A4%EA%B3%84-%EA%B2%8C%EC%8B%9C%ED%8C%90/dashboard)
