# Java 속성(Attributes)과 메서드(Methods)

속성(Attributes)은 객체에 대한 데이터를 저장하는 변수로, 객체의 상태나 특성을 정의합니다.  
메서드(Methods)는 객체의 행동을 정의하는 함수로, 객체가 수행할 수 있는 동작이나 작업을 나타냅니다.

속성과 메서드는 함께 객체의 **데이터와 행동을 캡슐화**하여 클래스로 묶습니다.

---

## 속성(Attributes) 예시

```java
public class Car {
    String color; // 속성
    int speed;    // 속성
}
```

- `color`와 `speed`는 `Car` 객체의 상태를 저장하는 변수입니다.

---

## 메서드(Methods) 예시

```java
public class Car {
    String color;
    int speed;

    void accelerate() { // 메서드
        speed += 10;
    }
}
```

- `accelerate()`는 `Car` 객체가 실행할 수 있는 행동입니다.

---

## 요약

- **속성(Attributes)**: 객체의 상태나 특성을 저장하는 변수
- **메서드(Methods)**: 객체의 행동을 정의하는 함수
- 둘을 통해 클래스는 객체의 데이터와 행동을 하나로 묶어 관리합니다
