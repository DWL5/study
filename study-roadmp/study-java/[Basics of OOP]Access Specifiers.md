# Java 접근 지정자 (Access Specifiers)

접근 지정자(Access Specifiers 또는 Access Modifiers)는 클래스, 메서드, 생성자, 필드 등의  
**접근 가능 범위(Visibility)**를 제어하는 키워드입니다.  
어디서 해당 멤버를 접근할 수 있는지를 결정합니다.

자바는 네 가지 접근 지정자를 제공합니다:  
`private`, `default`(키워드 없음), `protected`, `public`

---

## 종류와 설명

| 지정자 | 접근 범위 | 설명 |
|--------|-----------|------|
| `private` | 같은 클래스 내 | 외부에서 접근 불가 |
| (default) | 같은 패키지 내 | 키워드 없이 선언, 패키지 내부에서만 접근 가능 |
| `protected` | 같은 패키지 + 자식 클래스 | 패키지 내부 또는 상속받은 클래스에서 접근 가능 |
| `public` | 어디서나 접근 가능 | 모든 클래스에서 자유롭게 접근 가능 |

---

## 예시

```java
public class Example {
    private int privateField;
    int defaultField;          // default 접근
    protected int protectedField;
    public int publicField;

    private void privateMethod() {}
    void defaultMethod() {}      // default 접근
    protected void protectedMethod() {}
    public void publicMethod() {}
}
```

---

## 요약

- `private`: 클래스 내부 전용
- `default`: 같은 패키지 내에서만 접근
- `protected`: 패키지 + 상속 관계에서 접근 가능
- `public`: 모든 곳에서 접근 가능
