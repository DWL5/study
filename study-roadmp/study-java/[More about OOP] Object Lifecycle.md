# Java 객체 생명주기 (Object Lifecycle)

## 1. 객체 생성 (Creation)

- `new` 키워드를 사용해 클래스로부터 객체를 생성한다.
- 생성 시 메모리(Heap)에 인스턴스가 할당된다.
- 생성자(Constructor)가 호출되어 필드를 초기화한다.

```java
MyClass obj = new MyClass();
```

## 2. 객체 사용 (Usage)

- 생성된 객체를 통해 필드에 접근하거나 메서드를 호출한다.
- 객체는 참조를 통해 계속 접근 가능하다.

```java
obj.doSomething();
int x = obj.value;
```

## 3. 객체 소멸 준비 (Eligible for Garbage Collection)

- 객체에 대한 참조(reference)가 모두 끊기면,  
  JVM의 가비지 컬렉터(Garbage Collector)가 해당 객체를 "쓰레기"로 간주한다.

```java
obj = null;
// 또는
obj = new MyClass(); // 이전 객체는 참조가 끊긴 상태
```

## 4. 가비지 수집 (Garbage Collection)

- JVM은 주기적으로 메모리를 검사하고, 참조되지 않는 객체를 Heap 메모리에서 제거한다.
- 개발자가 직접 메모리 해제를 할 필요는 없다.

```java
System.gc(); // 가비지 컬렉션 요청 (바로 실행되지는 않을 수 있음)
```

## 요약 표

| 단계 | 설명 |
|------|------|
| 생성 | new 키워드를 통해 객체 생성 (Heap 메모리에 적재) |
| 사용 | 필드, 메서드 등을 통해 객체를 활용 |
| 소멸 준비 | 참조가 모두 끊긴 객체는 GC 대상이 됨 |
| 소멸 | JVM이 가비지 컬렉션으로 메모리 해제 |

## 추가 개념

- `Heap 메모리`: 객체 인스턴스가 저장되는 영역
- `Reachability Analysis(도달 가능성 분석)`으로 가비지 컬렉션 대상 판별
- `Strong Reference`가 끊겨야 가비지 컬렉션 대상이 됨

## 최종 요약

> 자바 객체는 생성(new) → 사용 → 참조 해제 → GC 수거 순서로 관리된다.  
> 가비지 컬렉션은 자동으로 동작하며, 개발자가 직접 메모리를 해제할 필요는 없다.
