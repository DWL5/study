# Java static 키워드

`static` 키워드는 특정 클래스의 인스턴스가 아닌 **클래스 자체에 속하는 멤버(변수, 메서드)**를 만들 때 사용합니다.  
static 멤버는 모든 객체가 **공유**하며, **객체 생성 없이 클래스 이름으로 직접 접근**할 수 있습니다.

---

## static 변수

- 클래스에 속한 변수
- 모든 객체가 하나의 값을 공유

```java
public class Example {
    static int count = 0;
}
```

```java
Example.count++;
```

---

## static 메서드

- 클래스에 속한 메서드
- 객체를 생성하지 않고 호출할 수 있음
- **static 변수만 접근 가능**하며, **static 메서드만 호출** 가능

```java
public class MathUtil {
    static int add(int a, int b) {
        return a + b;
    }
}
```

```java
int sum = MathUtil.add(3, 5);
```

---

## static과 JVM 메모리 구조

- static 멤버는 JVM의 **메서드 영역(Method Area)** 또는 **메타스페이스(Metaspace, Java 8 이상)**에 저장됩니다.
- 프로그램 시작 시 클래스가 로딩될 때 메모리에 올라가며, 프로그램 종료까지 유지됩니다.
- 객체를 여러 개 만들어도 static 변수는 **하나만 존재**합니다.

### 메모리 구조 요약

| 구역 | 역할 |
|------|------|
| Heap | 인스턴스(객체) 저장 |
| Stack | 메서드 호출 시 생성되는 지역 변수 저장 |
| Method Area (또는 Metaspace) | 클래스 정보와 static 변수, static 메서드 저장 |

---

## 요약

- `static`은 클래스 수준에 속하는 변수와 메서드를 정의
- 객체 생성 없이 **클래스명.멤버명**으로 접근
- static 멤버는 **메서드 영역(Method Area)**에 저장되며, 하나만 존재
- static 메서드는 static 멤버만 사용할 수 있음
