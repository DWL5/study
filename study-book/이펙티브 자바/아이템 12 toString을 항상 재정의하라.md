# 아이템 12: toString을 항상 재정의하라

**요약**

- 모든 클래스에서 toString 메서드를 재정의하여 객체의 **유익하고 읽기 쉬운 문자열 표현**을 반환하라. 이를 통해 디버깅이 쉬워지고, 사용자는 객체의 내용을 쉽게 이해할 수 있다.

**toString의 기본 동작**

- 기본적으로 Object.toString 메서드는 **클래스 이름과 해시코드**를 반환한다.

```java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```

- 예

```java
class Example {}
Example obj = new Example();
System.out.println(obj); // 출력: Example@1b6d3586
```

**toString을 재정의하는 이유**

1.	**가독성 향상**

- 기본 toString은 객체의 실제 상태를 제공하지 않아 의미 없는 정보만 출력.
- 재정의하면 객체의 **중요 정보**를 포함한 유익한 정보를 출력할 수 있음.

2.	**디버깅 편의성**

- 객체를 로깅하거나 디버깅할 때 객체 상태를 한눈에 확인 가능.

3.	**문서화 역할**

- 클래스의 주요 데이터를 나타내므로, 클래스 사용자가 객체를 쉽게 이해할 수 있음.

**toString 재정의의 예시**

- **올바른 toString 구현**

```java
class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        Person person = new Person("Alice", 30);
        System.out.println(person);
        // 출력: Person{name='Alice', age=30}
    }
}
```

**특징:**

- 클래스 이름, 필드 이름, 필드 값을 포함해 **간결하고 읽기 쉬운 표현**을 제공.
- 객체의 중요한 정보를 포함해 **유용한 디버깅 도구**로 활용 가능.

**toString 구현 시 주의사항**

1.	**간결하고 유익하게 작성**

- 너무 길거나 불필요한 정보를 포함하지 않는다.
- 예를 들어, 모든 내부 구현 세부 정보를 포함하면 오히려 가독성이 떨어질 수 있음.

2.	**객체의 중요한 정보만 포함**

- 클래스 사용자가 알아야 할 주요 필드만 출력하라.
    - 예: 비밀번호나 민감한 정보는 포함하지 말 것.

3.	**API 문서화에 활용**

- toString을 어떻게 정의했는지 문서화하면 클래스 사용자가 예상 가능한 결과를 얻을 수 있음.

4.	**상태가 변경될 가능성이 있는 경우 동적 표현 사용**

- 객체의 상태가 변경될 경우, toString에서 항상 최신 정보를 반영하도록 작성.

**toString을 재정의하지 않아도 되는 경우**

1.	**값 클래스가 아닌 경우**

- 예: 컨트롤러, 헬퍼 클래스 등.
- 이 경우 toString을 재정의하는 것은 큰 의미가 없을 수 있음.

2.	**유틸리티 클래스**

- 예: Collections 같은 정적 메서드만 있는 클래스.
- toString을 호출할 일이 없으므로 기본 동작으로 충분.

**자동 생성 활용**

- IDE나 도구를 사용해 간단히 toString을 생성할 수 있다.
    - 예: IntelliJ IDEA, Eclipse
- 자동으로 주요 필드를 포함한 toString 메서드를 생성.

**toString의 응용**

- **컬렉션 클래스 사용 시 편리**
    - toString이 잘 정의된 객체를 컬렉션에 추가하면, 컬렉션의 내용을 확인할 때도 유용.

```java
List<Person> people = List.of(new Person("Alice", 30), new Person("Bob", 25));
System.out.println(people);
// 출력: [Person{name='Alice', age=30}, Person{name='Bob', age=25}]
```

- **로깅 및 디버깅**
    - 로깅 시 객체의 toString이 잘 정의되어 있으면 로그가 더 유익해짐.

```java
log.info("Processing person: {}", person);
```

**잘못된 toString 구현**

- **불필요하게 자세한 정보**
    - 너무 많은 정보를 포함해 가독성이 떨어짐.
    - 중요한 정보에 집중해야 함.

```java
@Override

public String toString() {

return "Person{name='" + name + "', age=" + age + ", hashCode=" + hashCode() + ", memoryAddress=" + System.identityHashCode(this) + "}";

}
```

- **민감한 정보 포함**
    - 비밀번호나 인증 토큰과 같은 민감한 정보를 포함하지 말아야 함.

```java
@Override
public String toString() {
    return "User{username='" + username + "', password='" + password + "'}";
}
```

**결론**

- toString은 객체의 **유익하고 읽기 쉬운 문자열 표현**을 제공해야 한다.
- 객체의 주요 필드만 포함하며, 간결하고 유용하게 설계하라.
- 민감한 정보는 포함하지 말고, IDE를 활용해 자동 생성하거나 간결하게 작성하라.
- toString의 잘 정의된 구현은 디버깅과 로깅에서 큰 도움이 된다.
