# 아이템 5: 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라

- 핵심 내용
    - 문제 : 클래스에서 사용하는 자원을 직접 명시하면 코드가 재사용성, 유연성, 테스트 가능성 측면에서 제한적이 됨
    - 해결 방법 : 필요한 자원을 외부에서 주입받는 방식 (의존 객체 주입, Dependency Injection)을 사용하라

**왜 의존 객체 주입이 필요한가?**

- 재사용성 향상
    - 동일한 코드를 여러 환경에서 재사용 가능
    - 예 : 다른 데이터베이스, API, 파일 처리 등 다양한 자원을 처리할 수 있음
- 테스트 가능성 증가
    - 외부에서 자원을 주입받기 때문에, 테스트 시 모의 객체를 주입하여 독립적으로 테스트 가능
- 유연성 제공
    - 자원 변경 시 코드 수정 없이 주입 객체만 바꾸면 됨

**문제점: 직접 자원을 명시한 경우**

- **문제점**:
    - SpellChecker는 항상 같은 Lexicon 구현체를 사용해야 함.
    - 다른 Lexicon을 테스트하거나 확장하려면 클래스 자체를 수정해야 함.

```java
public class SpellChecker {
    private final Lexicon dictionary;

    public SpellChecker() {
        this.dictionary = new Lexicon(); // 직접 생성
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
```

**의존 객체 주입을 통한 해결**

- 의존 객체 주입을 사용하면, 클래스가 자원을 직접 생성하는 대신 외부에서 필요한 자원을 주입받습니다

```java
public class SpellChecker {
    private final Lexicon dictionary;

    // 생성자 주입 방식
    public SpellChecker(Lexicon dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }
}
```

- 장점
    - Lexicon 구현체를 외부에서 자유롭게 주입 가능
    - 유연한 교체와 테스트가 가능

**테스트 가능성 증가**

- 의존 객체 주입은 테스트에서 유리합니다 예를 들어, SpellChecker를 테스트할 때 실제 사전을 생성할 필요 없이 모의 객체를 주입하여 독립적으로 테스트할 수 있습니다

```java
public class MockLexicon implements Lexicon {
    @Override
    public boolean contains(String word) {
        return "mock".equals(word);
    }
}

SpellChecker spellChecker = new SpellChecker(new MockLexicon());
assert spellChecker.isValid("mock");
```

- 장점
    - 실제 자원을 사용할 필요가 없으므로 테스트 속도 향상
    - 테스트가 독립적이고 예측 가능해짐

### 핵심정리

- 클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다 이 자원들을 클래스가 직접 만들게 해서도 안 된다 대신 필요한 자원을 (혹은 그 자원을 만들어주는 팩터리를) 생성자에 (혹은 정적 팩터리나 빌더에) 넘겨주자
- 의존 객체 주입이라 하는 이 기법은 클래스의 유연성, 재사용성 테스트 용이성을 기막히게 개선해준다

### Supplier

- `Supplier<T>` 는 개체 생성이 지연되거나, 동적으로 생성 방식이 바뀔 수 있는 상황에서 유용합니다
- 지연 초기화
    - 객체 생성 비용이 높고, 반드시 초기화 시점에 필요하지 않는 경우
    - 객체가 필요할 때만 초기화하므로 성능 최적화 가능
    - 특히 큰 메모리를 차지하거나 생성 비용이 큰 객체에서 효과적

```java
public class LazyResource {
    private final Supplier<ExpensiveObject> resourceSupplier;
    private ExpensiveObject resource;

    public LazyResource(Supplier<ExpensiveObject> resourceSupplier) {
        this.resourceSupplier = resourceSupplier;
    }

    public ExpensiveObject getResource() {
        if (resource == null) {
            resource = resourceSupplier.get(); // 실제로 필요한 시점에 객체 생성
        }
        return resource;
    }
}

public class ExpensiveObject {
    public ExpensiveObject() {
        System.out.println("ExpensiveObject created!");
    }
}

// 사용
public static void main(String[] args) {
    LazyResource lazyResource = new LazyResource(ExpensiveObject::new);
    System.out.println("LazyResource initialized.");
    lazyResource.getResource(); // 여기서 객체 생성
    lazyResource.getResource(); // 이미 생성된 객체를 반환
}
```