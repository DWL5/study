# [DataStructure] Queue (큐) 정리 - Java 기준

## Queue란?
- First-In-First-Out (FIFO) 구조의 자료구조
- 먼저 들어온 데이터가 먼저 나가는 형태
- 줄 서기처럼 선입선출 방식

---

## 주요 연산

| 연산       | 설명                             |
|------------|----------------------------------|
| enqueue    | 데이터 삽입 (뒤에 추가)            |
| dequeue    | 데이터 삭제 (앞에서 제거)          |
| peek       | 맨 앞 요소를 제거하지 않고 조회     |
| isEmpty    | 큐가 비어 있는지 확인               |

---

## 특징

| 항목          | 설명 |
|---------------|------|
| 접근 방식     | 맨 앞(front)과 맨 뒤(rear)로 제한된 접근 |
| 삽입/삭제 속도 | O(1) (일반적으로) |
| 구현 방식     | 배열, LinkedList 기반 둘 다 가능 |

---

## 자바에서 큐 구현 방법

### LinkedList로 큐 사용
```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueExample {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        queue.add(1);
        queue.add(2);
        queue.add(3);

        System.out.println(queue.poll()); // 1
        System.out.println(queue.peek()); // 2
        System.out.println(queue.isEmpty()); // false
    }
}
```

### ArrayDeque로 큐 사용 (권장)
```java
import java.util.ArrayDeque;
import java.util.Queue;

public class ArrayDequeExample {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();

        queue.offer("Apple");
        queue.offer("Banana");
        queue.offer("Cherry");

        System.out.println(queue.poll()); // Apple
        System.out.println(queue.peek()); // Banana
    }
}
```
> offer() / poll()은 add() / remove()와 비슷하지만 실패 시 예외를 던지지 않고 false를 반환합니다.

---

## 큐의 확장 종류
- 일반 큐 (FIFO)
- 원형 큐 (Circular Queue)
- 우선순위 큐 (Priority Queue)
- 이중 큐 (Deque, Double-Ended Queue)

---

## 한 줄 요약
> Queue는 "먼저 들어간 데이터가 먼저 나온다". 자바에서는 LinkedList나 ArrayDeque로 쉽게 구현할 수 있다.

# [DataStructure] PriorityQueue (우선순위 큐) 정리 - Java 기준

## PriorityQueue란?
- **우선순위(priority)** 에 따라 요소를 정렬하여 관리하는 큐
- 기본적으로 **작은 값(낮은 숫자)**이 가장 높은 우선순위를 가짐 (Min-Heap 구조)

---

## 주요 연산

| 연산       | 설명                                  |
|------------|---------------------------------------|
| offer      | 요소 추가 (우선순위에 맞춰 자동 정렬)  |
| poll       | 가장 높은 우선순위 요소 제거 및 반환   |
| peek       | 가장 높은 우선순위 요소 조회 (제거X)   |
| isEmpty    | 큐가 비어 있는지 확인                  |

---

## 특징

| 항목          | 설명 |
|---------------|------|
| 내부 구조     | 힙(Heap) 자료구조 기반 |
| 삽입/삭제 속도 | O(log n) |
| 정렬 기준     | 기본적으로 오름차순 (Comparator로 커스텀 가능) |

---

## 자바에서 PriorityQueue 사용 방법

```java
import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        pq.offer(3);
        pq.offer(1);
        pq.offer(2);

        System.out.println(pq.poll()); // 1
        System.out.println(pq.poll()); // 2
        System.out.println(pq.poll()); // 3
    }
}
```

### Comparator로 내림차순 설정
```java
import java.util.PriorityQueue;
import java.util.Collections;

public class PriorityQueueDescExample {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        pq.offer(3);
        pq.offer(1);
        pq.offer(2);

        System.out.println(pq.poll()); // 3
    }
}
```

---

## 한 줄 요약
> PriorityQueue는 "우선순위가 높은 요소를 먼저 처리하는 큐"이며, 기본적으로 Min-Heap을 사용한다.

# [DataStructure] Deque (덱) 정리 - Java 기준

## Deque란?
- **Double-Ended Queue**의 줄임말
- 양쪽 끝(front와 rear) 모두에서 **삽입/삭제가 가능한 큐**

---

## 주요 연산

| 연산                 | 설명 |
|----------------------|------|
| addFirst / offerFirst | 앞쪽에 데이터 삽입 |
| addLast / offerLast   | 뒤쪽에 데이터 삽입 |
| pollFirst             | 앞쪽 데이터 제거 및 반환 |
| pollLast              | 뒤쪽 데이터 제거 및 반환 |
| peekFirst             | 앞쪽 데이터 조회 (제거X) |
| peekLast              | 뒤쪽 데이터 조회 (제거X) |
| isEmpty               | 덱이 비어 있는지 확인 |

---

## 특징

| 항목          | 설명 |
|---------------|------|
| 삽입/삭제 위치 | 앞, 뒤 모두 가능 |
| 삽입/삭제 속도 | O(1) |
| 구현 방식     | ArrayDeque, LinkedList로 구현 가능 |

---

## 자바에서 Deque 사용 방법

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DequeExample {
    public static void main(String[] args) {
        Deque<String> deque = new ArrayDeque<>();

        deque.offerFirst("First");
        deque.offerLast("Last");

        System.out.println(deque.pollFirst()); // First
        System.out.println(deque.pollLast());  // Last
    }
}
```

---

## Deque 사용 예시
- 양방향 탐색 (예: 양쪽 끝에서 진행하는 알고리즘)
- 슬라이딩 윈도우 문제
- Palindrome 검사

---

## 한 줄 요약
> Deque는 "앞/뒤 양쪽에서 삽입과 삭제가 모두 가능한 큐"이다.
