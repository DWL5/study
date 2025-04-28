# Java 데이터 타입 정리

자바의 데이터 타입은 크게 **기본형(Primitive Types)**, **참조형(Reference Types)**으로 나뉩니다.  

---

## 1. 기본형 데이터 타입 (Primitive Types)

| 타입 | 크기 | 기본값 | 설명 |
|------|------|--------|------|
| `byte` | 1 byte | 0 | -128 ~ 127 범위의 정수 |
| `short` | 2 bytes | 0 | -32,768 ~ 32,767 |
| `int` | 4 bytes | 0 | 가장 많이 사용되는 정수형 |
| `long` | 8 bytes | 0L | 큰 정수값 저장 시 사용 |
| `float` | 4 bytes | 0.0f | 소수점 표현, 단정도 실수 |
| `double` | 8 bytes | 0.0 | 더 정밀한 실수 표현 (기본 실수형) |
| `char` | 2 bytes | '\u0000' | 유니코드 문자 하나 저장 |
| `boolean` | 1 bit (논리적) | false | true 또는 false |

---

## 2. 참조형 데이터 타입 (Reference Types)

| 타입 | 설명 |
|------|------|
| `String` | 문자열 객체 |
| 배열 (`int[]`, `String[]`) | 여러 값을 순서대로 저장 |
| 클래스 | 사용자 정의 객체 |
| 인터페이스 | 다형성을 위한 타입 |
| `null` | 객체 없음 (참조 없음) |

> 참조형은 모두 힙 메모리에 저장되며 `new` 키워드로 객체를 생성합니다 (예외: String 리터럴)

---

## 3. 열거형 (Enum)

`enum`은 상수들의 집합을 정의할 수 있는 특수 클래스입니다.

```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY
}
```

- 타입 안정성 있는 상수 사용 가능
- 메서드 및 필드 정의 가능
- `switch`문에서도 자주 사용됨

---

## 4. 레코드 (Record) – Java 14+

`record`는 불변(immutable) 데이터를 간결하게 표현하는 클래스입니다.

```java
public record Person(String name, int age) {}
```

- 컴파일러가 생성자, getter, `toString`, `equals`, `hashCode` 자동 생성
- 불변 객체용 DTO, 값 객체에 유용
- 참조형이며 클래스의 일종

---

## 5. Wrapper 클래스 (Boxing)

기본형에 대응되는 참조형 클래스입니다.

| 기본형 | 박싱 클래스 |
|--------|--------------|
| `int` | `Integer` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

- 오토박싱 / 언박싱 지원 (`int ↔ Integer` 자동 변환)
- 컬렉션에서 사용 필수 (`List<int>` → ❌ → `List<Integer>`)

---

## 정리 요약

- **Primitive Types**: 값 자체 저장, 빠르고 메모리 효율적
- **Reference Types**: 객체 참조 저장, 유연하지만 메모리 비용 있음
- **Enum**: 상수 그룹을 명확하게 정의할 때 사용
- **Record**: 데이터를 표현하기 위한 불변 객체
