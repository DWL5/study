# [DataStructure] Array

## 정의
- 크기가 고정된, 같은 타입의 데이터를 연속된 메모리 공간에 저장하는 선형 자료구조

## 접근 속도
- 인덱스를 통해 임의 접근(Random Access) 가능
- 접근 시간: O(1)

## 삽입/삭제
- 중간에 데이터를 삽입하거나 삭제할 경우
  → 요소 이동이 필요해 O(n)의 시간이 걸림

## 장점
- 구조가 단순함
- 메모리 접근이 빠름
- 캐시 적중률(Cache Hit Rate)이 높음

## 단점
- 크기 변경이 불가능
- 삽입/삭제에 비효율적  
  → 가변적인 데이터에는 부적합

---

# 캐시 적중률(Cache Hit Rate)

## 개념
- CPU가 데이터를 읽을 때, 먼저 캐시 메모리에서 검색
  - 찾으면 → 캐시 히트(Cache Hit)
  - 못 찾고 메인 메모리에서 가져오면 → 캐시 미스(Cache Miss)
- 히트율이 높을수록 빠른 성능을 기대할 수 있음

## 배열이 캐시 히트율이 높은 이유

1. 연속된 메모리 구조
   - 배열의 요소들은 물리적으로 연속적으로 저장됨
   - 하나의 요소를 가져올 때, 주변 값까지 미리 캐시에 로딩됨 (Spatial Locality)

2. 접근 방식이 순차적
   - `for (int i = 0; i < arr.length; i++)` 처럼 순서대로 접근하는 경우가 많음
   - 이미 캐시에 올라온 값을 재사용하게 됨

---

# 자바의 ArrayList는 어떻게 구현되었나?

- Java의 ArrayList는 내부적으로 배열 (`Object[]`) 을 사용함
- 가변 크기를 지원하기 위해:
  - 요소 추가 시 배열의 공간이 부족하면
  - 더 큰 배열을 새로 생성 후, 기존 요소들을 복사함

> 이 덕분에 ArrayList는 사용자 입장에선 크기 제약 없이 사용 가능하지만,  
> 내부적으로는 배열 복사라는 비용이 발생할 수 있음