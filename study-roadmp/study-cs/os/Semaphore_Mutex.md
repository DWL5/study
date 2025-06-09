# 세마포어(Semaphore)와 뮤텍스(Mutex) 정리

---

## 1. 세마포어(Semaphore)란?

> **공유 자원에 접근할 수 있는 스레드 수를 제한하는 동기화 도구.**

- 자원의 개수를 나타내는 **정수값을 기반으로 동작**
- 여러 스레드가 동시에 접근할 수 있도록 허용할 수 있음
- 자원이 부족할 경우 **대기** 상태로 들어감

### 🔧 주요 연산
- `acquire()` (또는 `P()`, `wait()`): 자원 요청 (값 감소, 0이면 대기)
- `release()` (또는 `V()`, `signal()`): 자원 반환 (값 증가)

---

## 2. 뮤텍스(Mutex)란?

> **단 하나의 스레드만 자원에 접근할 수 있도록 보장하는 락(Lock) 개념.**

- 공유 자원에 대한 **단일 접근만 허용**
- 한 스레드가 락을 획득하면, **다른 스레드는 대기** 상태가 됨
- 락을 사용한 스레드만 **해제 가능**

---

## 3. 차이점 비교 표

| 항목 | 세마포어 (Semaphore) | 뮤텍스 (Mutex) |
|------|------------------------|----------------|
| 자원 수 | 여러 개 (Counting 가능) | 오직 하나 |
| 사용 목적 | 제한된 자원 접근 제어 | 임계구역 보호 |
| 해제 가능자 | 누구나 해제 가능 | **획득한 스레드만 해제 가능** |
| 동작 방식 | 정수 값 기반 | 락(잠금) 기반 |
| 종류 | 카운팅 세마포어, 이진 세마포어 | 없음 (기본형만 존재) |
| 예시 | 프린터 3대를 여러 스레드가 공유 | 데이터베이스 연결 하나에 대한 단독 접근 |

---

## 🧪 4. 예제 코드 (Java)

### 세마포어 예시
```java
Semaphore sem = new Semaphore(3); // 자원 3개

sem.acquire();  // 접근 시작
// 자원 사용
sem.release();  // 자원 반납
```

``` java
Queue<Printer> printerPool = new LinkedList<>();
printerPool.add(new Printer("P1"));
printerPool.add(new Printer("P2"));
printerPool.add(new Printer("P3"));

printerPermit.acquire(); // 세마포어: 입장 가능 여부 확인

Printer myPrinter;
synchronized (printerPool) {
    myPrinter = printerPool.poll(); // 실제 프린터 하나 꺼냄
}

// 프린터 사용
myPrinter.print("문서 내용");

// 사용 끝났으면 프린터 반납
synchronized (printerPool) {
    printerPool.add(myPrinter);
}
printerPermit.release(); // 세마포어: 나갔다고 알림
```

### 뮤텍스 예시 (ReentrantLock)
```java
Lock lock = new ReentrantLock();

lock.lock();    // 락 획득
try {
    // 임계구역
} finally {
    lock.unlock();  // 락 해제
}
```

---

## 5. 요약 

> 세마포어는 여러 개의 자원 접근을 동시에 제어할 수 있는 동기화 도구이며, 값을 기반으로 동작합니다. 반면 뮤텍스는 오직 하나의 스레드만 자원을 사용할 수 있도록 보장하는 락 기반의 제어 도구입니다. 세마포어는 자원 수를 조절할 수 있지만, 뮤텍스는 단일 자원 보호에 최적화되어 있습니다.

---

## 6. 관련 개념 이어서 공부하기

- 스핀락(Spinlock)
- Condition Variable
- 임계 구역(Critical Section)
- 데드락, 라이브락, 기아(Starvation)
