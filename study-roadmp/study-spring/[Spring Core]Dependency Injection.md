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