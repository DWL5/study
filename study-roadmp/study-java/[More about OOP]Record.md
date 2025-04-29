# Java Record (레코드)

## 개념

- Record는 **불변 데이터 객체**를 간결하게 정의하기 위한 클래스 타입이다.
- Java 14에 도입, Java 16부터 정식 기능이다.
- 주로 DTO나 값 객체(Value Object)에 사용된다.

---

## 기본 문법

```java
public record Person(String name, int age) {}
```

자동 생성:
- 모든 필드는 `private final`
- 생성자, `getter`, `toString()`, `equals()`, `hashCode()`

---

## 사용 예시

```java
Person person = new Person("Alice", 30);

System.out.println(person.name()); // "Alice"
System.out.println(person.age());  // 30
System.out.println(person);        // "Person[name=Alice, age=30]"
```

---

## 특징 요약

| 특징 | 설명 |
|------|------|
| 불변성 | 모든 필드가 final |
| 간결성 | 코드가 짧고 명확 |
| 값 기반 비교 | equals(), hashCode() 자동 생성 |
| 추가 기능 | 메서드, 생성자 커스터마이징 가능 |

---

## 제약사항

- 다른 클래스를 상속할 수 없다 (`extends` 금지).
- 모든 필드 이름과 생성자 매개변수는 일치해야 한다.
- 필드는 수정할 수 없다 (setter 불가).

---

## 요약

> "Record는 값을 담는 객체를 간결하게 정의하는 자바의 새로운 기능이다.  
> 코드량을 줄이고, 불변 객체를 쉽게 만들 수 있다."
