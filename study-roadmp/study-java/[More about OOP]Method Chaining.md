# Java Method Chaining (메서드 체이닝)

## 개념

메서드 체이닝(Method Chaining)이란,  
**객체의 메서드 호출 결과로 다시 객체 자신(this)을 반환하여**  
여러 메서드를 하나의 연속된 표현으로 호출하는 프로그래밍 기법입니다.

주로 **가독성을 높이고 코드 흐름을 자연스럽게** 만들기 위해 사용합니다.

---

## 기본 구조

- 메서드가 `this`를 반환해야 메서드 체이닝이 가능하다.

```java
public class Person {
    private String name;
    private int age;

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public void printInfo() {
        System.out.println(name + " (" + age + ")");
    }
}
```

---

## 사용 예시

```java
Person person = new Person()
    .setName("Alice")
    .setAge(25);

person.printInfo();
// 출력: Alice (25)
```

- `setName()`이 `this`를 반환하므로, 바로 `setAge()`를 이어서 호출할 수 있다.

---

## 장점

- 코드가 **간결**하고 **읽기 쉽다**.
- 일관성 있는 객체 설정이 가능하다.
- **Builder 패턴** 구현 시 필수적으로 활용된다.

---

## 주의사항

- 메서드가 항상 `this`를 반환해야 한다.
- 과도한 체이닝은 오히려 **가독성을 해칠 수** 있으므로 주의가 필요하다.

---

## 최종 요약

> "메서드 체이닝은 메서드가 `this`를 반환하여 여러 메서드 호출을 자연스럽게 연결하는 기법이다."
