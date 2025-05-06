# 인덱스 (Index)

## 1. 인덱스란?

- 인덱스는 테이블의 데이터에 빠르게 접근하기 위해 사용하는 **자료구조**입니다.
- MySQL(InnoDB)에서는 **B+ Tree 인덱스**가 주로 사용됩니다.
- 인덱스를 사용하면 **조회 성능은 향상되지만**, 쓰기 속도와 저장 공간을 더 사용합니다.

---

## 2. 인덱스의 종류

| 종류 | 설명 |
|------|------|
| **Clustered Index (클러스터형 인덱스)** | - 테이블의 기본 키(PK)가 클러스터형 인덱스로 자동 생성됨<br>- leaf node에 **row 자체 데이터**를 포함함<br>- 데이터 자체가 정렬된 상태로 저장됨 |
| **Secondary Index (보조 인덱스)** | - 명시적으로 생성한 인덱스<br>- leaf node에 인덱스 컬럼 + PK 포함<br>- 데이터를 찾을 때 PK를 이용해 한 번 더 조회 (Back to Index) |

---

## 3. 게시판 인덱스 설계 예시

### 📌 인덱스 생성

```sql
CREATE INDEX idx_board_id_article_id 
ON article(board_id ASC, article_id DESC);
```

### ✅ 목적

- 게시판(board_id) 별로 **최신 글(article_id DESC)** 정렬
- `article_id`는 Snowflake 기반 전역 ID로 **정렬성과 고유성** 보장
- `created_at` 대신 사용하는 이유: **동시성 충돌 방지**

---

## 4. 페이지네이션 쿼리 & 성능 이슈

### Offset 기반 쿼리 예

```sql
SELECT * FROM article
WHERE board_id = 1
ORDER BY article_id DESC
LIMIT 30 OFFSET 90;
```

- `OFFSET`이 커질수록 **인덱스를 많이 스캔**해야 함 → **느려짐**
- 예: 1200만 건 중 4페이지 조회에도 4초 이상 소요되는 경우 있음

---

## 5. 대안: 기준점 방식 (No Offset)

### 기준점 기반 쿼리

```sql
SELECT * FROM article
WHERE board_id = 1
  AND article_id < {기준점}
ORDER BY article_id DESC
LIMIT 30;
```

### 장점

- `OFFSET` 없이 인덱스를 활용 → **성능 안정적**
- 무한 스크롤 구조에 적합

---

## 6. EXPLAIN을 통한 Query Plan 분석

### 예제

```sql
EXPLAIN
SELECT * FROM article
WHERE board_id = 1
ORDER BY article_id DESC
LIMIT 30 OFFSET 90;
```

### 주요 컬럼 해석

| 컬럼 | 의미 |
|------|------|
| `type` | 조인의 방식 (`ALL`, `range`, `ref` 등) |
| `key` | 사용된 인덱스 이름 |
| `rows` | 예측된 스캔 행 수 |
| `Extra` | 추가 정보 (`Using index`, `Using filesort` 등) |


### 나쁜 예 (느린 쿼리의 특징)
| id | select_type | table   | partitions | type | possible_keys | key  | key_len | ref  | rows    | filtered | Extra                       |
|----|-------------|---------|------------|------|----------------|------|---------|------|---------|----------|-----------------------------|
| 1  | SIMPLE      | article | NULL       | ALL  | NULL           | NULL | NULL    | NULL | 7992099 | 10.00    | Using where; Using filesort |

```
type: ALL
key: NULL
Extra: Using filesort
```

| 항목 | 의미 | 성능 영향 | 해결 방안 |
|------|------|------------|------------|
| `type: ALL` | 전체 테이블 스캔. 모든 row를 읽음 | ❌ 매우 느림 | WHERE 조건에 맞는 인덱스 사용 |
| `key: NULL` | 사용된 인덱스 없음 | ❌ 비효율적 | 적절한 (복합) 인덱스 생성 |
| `Extra: Using filesort` | 정렬을 위해 추가 정렬 단계 수행 | ❌ 불필요한 정렬 비용 발생 | ORDER BY에 맞는 인덱스 생성 |


> `Using filesort`가 있다면 **ORDER BY가 인덱스로 해결되지 못함** → 인덱스 최적화 필요

<br>

### 인덱스 추가

```
CREATE INDEX idx_board_id_article_id ON article(board_id ASC, article_id DESC);
```

| Table   | Non_unique | Key_name                | Seq_in_index | Column_name | Collation | Cardinality | Index_type |
|---------|------------|-------------------------|--------------|-------------|-----------|-------------|------------|
| article | 0          | PRIMARY                 | 1            | article_id  | A         | 7928752     | BTREE      |
| article | 1          | idx_board_id_article_id | 1            | board_id    | A         | 1           | BTREE      |
| article | 1          | idx_board_id_article_id | 2            | article_id  | D         | 7947914     | BTREE      |


<br>

### 좋은 예

```sql
EXPLAIN
SELECT * FROM article
WHERE board_id = 1
ORDER BY article_id DESC
LIMIT 30 OFFSET 90;
```

| id | select_type | table   | type | possible_keys           | key                     | key_len | ref   | rows    | filtered | Extra |
|----|-------------|---------|------|--------------------------|-------------------------|---------|-------|---------|----------|--------|
| 1  | SIMPLE      | article | ref  | idx_board_id_article_id | idx_board_id_article_id | 8       | const | 3996049 | 100.00   | NULL   |

```
type: ref
key: idx_board_id_article_id
Extra NULL
```


| 항목 | 내용 | 평가 |
|------|------|------|
| `type: ref` | `board_id = 1` 조건으로 인덱스 탐색 | ✅ 좋음 |
| `key: idx_board_id_article_id` | `board_id + article_id` 복합 인덱스 사용 | ✅ 아주 좋음 |
| `Extra: NULL` | `ORDER BY article_id DESC`가 인덱스로 커버됨 → `filesort` 없음 | ✅ 매우 좋음 |


---

## 7. 인덱스 성능 최적화 팁

- `WHERE`와 `ORDER BY`에 사용하는 컬럼은 **복합 인덱스**로 묶자
- **인덱스 순서**는 쿼리 조건에 맞춰야 효과적
- 자주 변경되는 컬럼에 인덱스를 남발하지 말 것 (INSERT/UPDATE 성능 저하)
- `created_at`을 기준으로 정렬할 땐 **동시 생성 이슈**로 충돌 가능성 있음
- 인덱스 컬럼에 함수 (`DATE(created_at)`) 사용 시 **인덱스 무효화**

---

## 8. 요약

| 항목 | 설명 |
|------|------|
| Clustered Index | PK 기준 정렬, row data 포함 |
| Secondary Index | leaf에 PK 포함, 다시 Clustered Index 조회 |
| Offset 쿼리 | 느려짐. 인덱스 스캔 많아짐 |
| 기준점 쿼리 | 고정 성능, 무한 스크롤 적합 |
| EXPLAIN | 인덱스 사용 여부와 성능 확인 |
| 인덱스 최적화 | 복합 인덱스 + 정렬 순서 고려 필수 |

---

## 📚 참고 강의

[스프링부트로 대규모 시스템 설계 - 게시판 프로젝트 (Inflearn)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8%EB%A1%9C-%EB%8C%80%EA%B7%9C%EB%AA%A8-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%84%A4%EA%B3%84-%EA%B2%8C%EC%8B%9C%ED%8C%90/dashboard)
