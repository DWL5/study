## Dependency Injection (DI)

- 객체가 필요한 의존성을 외부에서 주입받는 것

### 강한 결합 (OCP, DIP 위반)

- 문제점
    - OCP 위반 : 정책 변경 시 OrderService 코드도 변경해야함
    - DIP 위반 : 구체 클래스 (FixedDiscountPolicy)에 직접 의존하고 있음

```java
public class OrderService {
    private final FixedDiscountPolicy discountPolicy = new FixedDiscountPolicy(); // 직접 생성

    public int calculatePrice(int price) {
        return price - discountPolicy.discount(price);
    }
}
```

### DI 적용 (하지만 여전히 DIP 위반)

- DI를 적용했지만 OCP와 DIP 둘 다 충족시키지 못함
    - OCP 위반 : 정책 변경 시 OrderService 코드도 변경해야함
    - DIP 위반 : 구체 클래스 (FixedDiscountPolicy)에 직접 의존하고 있음

```java
public class OrderService {
    private final FixedDiscountPolicy discountPolicy;

    public OrderService(FixedDiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public int calculatePrice(int price) {
        return price - discountPolicy.discount(price);
    }
}
```

### 인터페이스 기반 DI 적용 (OCP + DIP 만족)

- OCP
    - OrderService는 새로운 할인 정책이 생겨도 수정하지 않음
    - 유연한 확장성
        - 할인 정책을 쉽게 교체/확장 가능
- DIP
    - OrderService는 추상화에만 의존함
- 테스트 용이성
    - 다양한 Mock DiscountPolicy로 테스트 가능

```java
// 할인 정책 인터페이스
public interface DiscountPolicy {
    int discount(int price);
}
```

```java
// 고정 할인 정책 구현
public class FixedDiscountPolicy implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return 1000;
    }
}
```

```java
// 퍼센트 할인 정책 구현
public class RateDiscountPolicy implements DiscountPolicy {
    @Override
    public int discount(int price) {
        return price * 10 / 100;
    }
}
```

```java
// 서비스는 인터페이스에만 의존
public class OrderService {
    private final DiscountPolicy discountPolicy;

    public OrderService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public int calculatePrice(int price) {
        return price - discountPolicy.discount(price);
    }
}
```

# Dependency Injection (DI) 방식

## 1. 생성자 주입 (Constructor Injection)

- 설명
  - 생성자를 통해 필요한 의존 객체를 주입하는 방식
  - 의존성 주입이 필수적으로 보장됨
- 특징
  - 불변성(immutable) 유지 가능
  - 테스트 작성이 용이함
  - 주입받지 못하면 컴파일 에러 발생

```java
@Component
public class OrderService {

    private final MemberRepository memberRepository;

    @Autowired // 생략 가능 (생성자가 1개면)
    public OrderService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

---

## 2. 수정자 주입 (Setter Injection)

- 설명
  - Setter 메서드를 통해 의존 객체를 주입하는 방식
- 특징
  - 선택적 의존성에 유리
  - 객체 생성 후 주입되므로 주입 시점을 늦출 수 있음
  - 의존성 없이도 객체 생성 가능 (초기값 null 위험)

```java
@Component
public class OrderService {

    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```

---

## 3. 필드 주입 (Field Injection)

- 설명
  - 필드에 직접 `@Autowired`를 붙여 의존 객체를 주입하는 방식
- 특징
  - 코드가 간편하지만 테스트 불리
  - 유지보수와 확장성에 약함
  - 권장하지 않음 (프레임워크 내부나 간단한 데모용)

```java
@Component
public class OrderService {

    @Autowired
    private MemberRepository memberRepository;
}
```

---

## 4. 일반 메서드 주입 (General Method Injection)

- 설명
  - 일반 메서드를 통해 의존성을 주입하는 방식
  - 메서드 이름 자유, 여러 의존성 주입 가능
- 특징
  - 가독성이 떨어질 수 있음
  - 특별한 경우에만 사용

```java
@Component
public class OrderService {

    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

---

# Dependency Injection 방식 비교

| 구분 | 특징 | 사용 상황 |
| --- | --- | --- |
| 생성자 주입 | 불변성 보장, 테스트 용이 | **필수 의존성 주입 (권장)** |
| 수정자 주입 | 선택적 의존성 처리 가능 | 선택적/후주입 의존성 |
| 필드 주입 | 코드 간단, 테스트 불리 | 비권장 (프레임워크 내부, 데모) |
| 일반 메서드 주입 | 메서드명 자유, 복수 주입 | 특별한 경우 |

---

# 생성자 주입이 권장되는 이유

1. **불변성 보장**
   - `final` 키워드를 사용할 수 있어, 주입 이후 객체 상태 변경을 막을 수 있다.

2. **필수 의존성 보장**
   - 주입받지 못하면 컴파일 오류 또는 런타임 오류가 발생해 빠르게 문제를 찾을 수 있다.

3. **테스트 편의성**
   - 테스트 코드에서 Mock 객체를 생성자 주입으로 쉽게 주입할 수 있다.

4. **순환 참조 감지**
   - 스프링은 생성자 주입 시점에 순환 참조를 조기에 감지해준다.

---

# 순환 참조(Circular Dependency)와 생성자 주입

## 순환 참조란?

- A 객체가 B 객체를 참조하고
- B 객체가 다시 A 객체를 참조하는 구조

## 문제점

- 서로 의존하고 있어 객체 생성이 완료되지 못하는 문제가 발생함

## 생성자 주입 시 순환 참조

- **즉시 객체 생성을 요구**하기 때문에
- 스프링이 앱 시작 시점에 **순환 참조를 즉시 감지하고 오류 발생시킴**
- → **런타임 오류를 방지하고 설계 문제를 빨리 발견**할 수 있음

## 필드/수정자 주입과 순환 참조

- 필드 주입이나 수정자 주입은 객체를 먼저 생성해놓고 나중에 의존성을 주입하므로
- 순환 참조가 늦게 감지되거나, 심지어 실행 중간에 문제가 발생할 수 있음

---

# 핵심 요약

> "생성자 주입은 객체 불변성을 지키고, 필수 의존성 문제를 조기에 발견할 수 있으며, 순환 참조를 빠르게 감지할 수 있기 때문에 가장 권장되는 주입 방식이다."
