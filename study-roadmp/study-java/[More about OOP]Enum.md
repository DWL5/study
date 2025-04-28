# Java Enum (열거형) 완벽 정리

## 1. Enum이란 무엇인가?

- Enum은 **고정된 상수 집합**을 표현하는 특별한 데이터 타입이다.
- 각 Enum 상수는 **해당 Enum 타입의 객체**로 간주된다.
- 예시:

```java
public enum Day {
    MONDAY, TUESDAY, WEDNESDAY
}
```

---

## 2. Enum을 사용하는 이유

| 문제점 (int/String 상수) | Enum 사용의 장점 |
|---------------------------|-------------------|
| 타입 안전성 없음           | 타입 안전성 보장 |
| 디버깅 어려움              | 가독성 향상, 이름 그대로 출력 |
| 관리 어려움                | 한곳에서 상수 집합 관리 가능 |
| 값 범위 제약 없음          | 고정된 범위만 허용 |

---

## 3. Effective Java - Item 34 요약

> "상수 대신 Enum을 사용하라."

Joshua Bloch는 다음 이유로 Enum 사용을 강력히 추천했다:

- **타입 안전성**을 보장할 수 있다.
- **가독성**과 **디버깅 편의성**이 개선된다.
- **코드 유지보수성**이 높아진다.
- Enum은 상수 이상의 역할(필드, 메서드 추가)이 가능해 확장성이 높다.

✅ **int나 String 상수로 상수 집합을 표현하는 것은 위험하다.**  
✅ **항상 enum을 사용해 명확하고 안전한 타입을 만들어라.**

---

## 4. Enum은 값 객체인가?

- ✅ Enum은 **불변(Immutable)**이고, **값(상수)만으로 의미**를 가진다.
- ✅ 별도의 식별자 없이 이름과 값으로 비교된다.
- ✅ 따라서 **Enum은 특별한 형태의 "값 객체(Value Object)"**로 볼 수 있다.

추가 특징:
- 각 Enum 인스턴스는 JVM이 관리하는 **싱글턴(singleton)**이다.
- `==` 비교도 가능 (`equals()` 호출 없이도 동등성 비교 가능).

---

## 5. Enum에 필드, 생성자, 메서드 추가하기

- Enum은 단순한 상수 이상으로 활용 가능하다.
- 필드와 메서드를 추가하여 확장할 수 있다.

```java
public enum Level {
    LOW(1), MEDIUM(2), HIGH(3);

    private final int code;

    Level(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
```

---

## 6. Enum 관련 주요 메서드

| 메서드 | 설명 |
|--------|------|
| `name()` | Enum 이름을 문자열로 반환 |
| `ordinal()` | Enum 정의 순서(0부터 시작) 반환 |
| `valueOf(String name)` | 이름으로 Enum 상수 가져오기 |

---

## 7. 추가 심화: 자바 참조 타입과 Enum

- 기본 참조 타입인 **Strong Reference**가 Enum에도 적용된다.
- Enum 상수는 프로그램 종료까지 **JVM Method Area**에 살아있고,  
  절대 가비지 컬렉션 대상이 되지 않는다.

---
