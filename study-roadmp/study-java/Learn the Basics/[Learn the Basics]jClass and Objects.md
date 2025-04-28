# Java 클래스(Class)와 객체(Object)

클래스는 객체를 만들기 위한 설계도(청사진)입니다.  
객체는 클래스에서 만들어진 **구체적인 인스턴스**입니다.

클래스는 객체가 가질 **특성(속성, attributes)** 과 **행동(메서드, methods)** 을 정의합니다.  
즉, 클래스를 템플릿(template)이라 생각하고, 객체는 그 템플릿으로 찍어낸 구체적인 결과물입니다.

---

## 클래스 선언

```java
public class Car {
    // 속성 (필드)
    String color;
    int speed;

    // 행동 (메서드)
    void accelerate() {
        speed += 10;
    }
}
```

---

## 객체 생성

```java
Car myCar = new Car(); // Car 클래스의 인스턴스 생성
myCar.color = "red";
myCar.accelerate();
```

---

## 요약

- **클래스**: 객체의 속성과 행동을 정의하는 설계도
- **객체**: 클래스로부터 생성된 구체적인 실체
- 클래스를 통해 여러 개의 객체를 만들 수 있으며, 각각 독립적으로 값을 가질 수 있음
