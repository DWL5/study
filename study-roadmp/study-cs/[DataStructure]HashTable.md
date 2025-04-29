# [DataStructure] Hash Table (해시 테이블) 정리

## Hash Table이란?
- Key를 해시 함수(Hash Function)에 넣어 나온 값을 기반으로 데이터를 저장하는 자료구조
- 빠른 검색, 삽입, 삭제를 지원하는 구조
- 배열 + 해시 함수의 조합

---

## 특징

| 항목        | 설명 |
|-------------|------|
| 데이터 저장 방식 | 해시 함수를 통해 계산된 인덱스에 데이터 저장 |
| 접근 속도    | 평균 O(1), 최악 O(n) (충돌 발생 시) |
| 구현 기반    | 내부적으로 배열 사용 |
| 주요 문제    | 해시 충돌(Collision) 관리 필요 |

---

## 주요 연산

| 연산   | 설명 |
|--------|------|
| put(key, value) | 키와 값을 삽입 |
| get(key) | 키에 해당하는 값을 조회 |
| remove(key) | 키에 해당하는 값을 삭제 |
| containsKey(key) | 특정 키 존재 여부 확인 |
| computeIfAbsent(key, mappingFunction) | 값이 없으면 계산해서 삽입 |

---

## 해시 충돌(Collision) 해결 방법
- **체이닝(Chaining)**: 같은 인덱스에 여러 개의 값을 LinkedList로 저장
- **오픈 어드레싱(Open Addressing)**: 충돌 발생 시 빈 공간을 찾아 저장 (Linear Probing 등)

---

## 자바에서 HashMap 사용 예시

```java
import java.util.HashMap;

public class HashMapExample {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        map.put("apple", 3);
        map.put("banana", 5);
        map.put("orange", 2);

        System.out.println(map.get("banana")); // 5
        System.out.println(map.containsKey("apple")); // true

        map.remove("orange");
        System.out.println(map.get("orange")); // null
    }
}
```

---

## Hash Table vs Array vs Tree 비교

| 항목          | Hash Table          | Array                 | Tree (BST 기준)        |
|---------------|----------------------|-----------------------|------------------------|
| 검색 속도      | 평균 O(1)             | O(1) (인덱스 알 때)    | O(log n)               |
| 삽입/삭제 속도 | 평균 O(1)             | O(n) (중간 삽입/삭제 시) | O(log n)               |
| 정렬 지원      | 불가                 | 인덱스 순 | 가능 (Inorder Traversal) |

---

## 한 줄 요약
> Hash Table은 "Key를 해시하여 빠르게 데이터를 찾는 구조"이며, 해시 충돌을 잘 처리하는 것이 성능의 핵심이다.

# [DataStructure] ConcurrentHashMap (동시성 해시맵) 정리

## ConcurrentHashMap이란?
- 멀티스레드 환경에서도 **동기화 문제 없이** 안전하게 사용할 수 있는 해시 테이블
- HashMap과 비슷하지만, 내부적으로 **락(lock)을 세밀하게 관리**하여 성능을 최적화함

---

## 주요 특징

| 항목        | 설명 |
|-------------|------|
| 동시성 지원   | 여러 스레드가 동시에 읽고 쓸 수 있음 |
| 내부 구조     | Segment → Java 8 이후엔 Bucket 수준의 Fine-Grained Lock |
| 락 전략       | 키가 충돌하지 않으면 락 없이 병렬 삽입/삭제 가능 |
| Null 허용 여부 | Key, Value 모두 null 허용 안 함 |

---

## 주요 메서드

| 메서드               | 설명 |
|----------------------|------|
| put(key, value)       | 키와 값을 삽입 (락을 걸어 안전하게) |
| get(key)              | 키로 값 조회 (락 없이도 읽기 가능) |
| remove(key)           | 키에 해당하는 값 삭제 |
| computeIfAbsent(key, mappingFunction) | 값이 없으면 계산해서 삽입 |
| containsKey(key) | 특정 키 존재 여부 확인 |

---

## 자바에서 ConcurrentHashMap 사용 예시

```java
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        map.put("apple", 3);
        map.put("banana", 5);

        System.out.println(map.get("apple")); // 3

        map.computeIfAbsent("orange", k -> 2);
        System.out.println(map.get("orange")); // 2
    }
}
```

---

## HashMap vs ConcurrentHashMap

| 항목               | HashMap                         | ConcurrentHashMap              |
|--------------------|----------------------------------|---------------------------------|
| 동시성             | 스레드 안전하지 않음               | 스레드 안전함                   |
| 락 전략             | 없음                             | 세분화된 락 (Segment, Bucket 수준) |
| 성능 (단일 스레드) | 빠름                             | 약간 느릴 수 있음               |
| null 허용 여부      | key, value 모두 가능               | key, value 모두 허용 안 함       |

---

## 주의할 점
- `ConcurrentHashMap`은 **단일 연산(put, get 등)** 은 안전하지만,  
  **여러 연산을 조합할 때는(예: get → put)** 여전히 외부에서 추가 동기화가 필요할 수 있다.

---

## 한 줄 요약
> ConcurrentHashMap은 "멀티스레드 환경에서도 고성능을 유지하면서 안전하게 동작하는 해시맵"이다.
