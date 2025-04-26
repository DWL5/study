# 아이템 10: equals는 일반 규약을 지켜 재정의하라

### 요약

- equals 메서드를 재정의할 때는 반드시 일반 규약을 준수해야 한다.
- 잘못된 구현은 프로그램의 동작에 예기치 못한 오류를 유발할 수 있다.

### equals 메서드의 일반 규약

1. 반사성
    1. 객체는 자기 자신과 같아야 한다.

```java
x.equals(x) == true
```

1. 대칭성
    1. 두 객체가 서로를 동등하다고 판단하면, 그 관계는 양방향이어야 한다.
    
    ```java
    x.equals(y) == y.equals(x)
    ```
    
2. 추이성
    1. x.equals(y)가 참이고, y.equals(z)가 참이라면, x.equals(z)도 참어야 한다.

```java
x.equals(y) && y.equals(z) -> x.equals(z)
```

1. 일관성

```java
x.equals(y)는 상태가 변하지 않으면 동일한 결과를 반환해야 한다.
```

1. null과 비교
    1. 모든 객체는 null과 비교하면 false를 반환해야 한다.

```java
x.equals(null) == false
```

### 올바른 equals 재정의 방법

1. ==로 자기 자신 확인
    1. this와 비교하여 동일한 객체인지 먼저 확인한다.

```java
if (this == obj) return true;
```

1. instanceof로 타입확인
    1. 비교 대상 객체가 올바른 타입인지 확인한다.

```java
if (!(obj instanceof MyClass)) return false;
```

1. 필드 값 비교
    1. 중요한 필드들을 비교하여 동등성을 확인한다.

```java
MyClass other = (MyClass) obj;
return this.field1.equals(other.field1) && this.field2 == other.field2;
```

```java
import java.util.Objects;

public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // 자기 자신과 비교
        if (!(obj instanceof Person)) return false; // 타입 확인

        Person other = (Person) obj;
        // 중요한 필드 값 비교
        return Objects.equals(name, other.name) && age == other.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age); // equals를 재정의하면 hashCode도 재정의해야 함
    }
}
```

### equals 재정의의 주의점

1. 동등성과 동일성 구분
    1. equals는 동등성을, ==는 동일성을 비교한다.
        1. 동등성 : 객체의 내용이 같은가?
        2. 동일성 : 두 객체의 참조가 같은가?
2. hashCode와 equals의 일관성 유지
    1. equals를 재정의하면 반드시 hashCode도 재정의해야 한다.
        1. 같은 객체라면 동일한 hashCode 값을 반환해야 함
3. instanceof 사용에 주의
    1. 특정 클래스에서만 동등성을 정의할 때는 instanceof 대신 클래스비교 getClass를 사용하는 경우도 있다.

### 결론

- equals 메서드를 재정의할 때는 바느시 일반규약을 준수해야 한다.
- 재정의 시에는 중요한 필드만 비교하고, hashCode도 함께 재정의해야 일관성이 보장된다.
- `Objects.equals` 와 같은 유틸리티 메서드를 활용하면 더 안전하고 간결하게 구현할 수 있다.
