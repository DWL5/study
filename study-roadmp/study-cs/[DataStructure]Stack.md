# [DataStructure] Stack (스택) 정리 - Java 기준

## Stack이란?
- Last-In-First-Out (LIFO) 구조의 자료구조
- 나중에 들어간 데이터가 가장 먼저 나오는 형태
- 접시 쌓기처럼 마지막에 올린 접시를 먼저 꺼내는 방식

---

## 주요 연산

| 연산    | 설명                          |
|---------|-------------------------------|
| push    | 데이터 삽입 (위에 추가)         |
| pop     | 데이터 삭제 (위에서 제거)       |
| peek    | 맨 위 요소를 제거하지 않고 조회 |
| isEmpty | 스택이 비어 있는지 확인         |

---

## 특징

| 항목          | 설명                         |
|---------------|------------------------------|
| 접근 방식     | 맨 위(top) 요소만 접근 가능   |
| 삽입/삭제 속도 | O(1) (항상 위에서 삽입/삭제) |
| 구현 방식     | 배열, LinkedList 기반 모두 가능 |

---

## 자바에서 스택 구현 방법

### Stack 클래스 사용
```java
import java.util.Stack;

public class StackExample {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());  // 3
        System.out.println(stack.peek()); // 2
        System.out.println(stack.isEmpty()); // false
    }
}
```

---

## 스택의 확장 사용 예시
- 수식 계산 (괄호 짝 맞추기)
- 백트래킹 (DFS)
- Undo 기능 (되돌리기)

---

## 스택 vs 큐 비교

| 항목          | 스택 (Stack)           | 큐 (Queue)          |
|---------------|-------------------------|---------------------|
| 구조           | LIFO (후입선출)           | FIFO (선입선출)       |
| 삽입/삭제 위치 | 항상 top                 | 앞(front) 삽입, 뒤(rear) 삭제 |
| 사용 예시      | 함수 호출 스택, Undo 기능 | 작업 대기열, 프린터 대기열 |

---

## 한 줄 요약
> Stack은 "나중에 들어간 데이터가 먼저 나온다". 자바에서는 Stack 클래스로 쉽게 구현할 수 있다.