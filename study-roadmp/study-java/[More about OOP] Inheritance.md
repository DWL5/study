## 1. Java Inheritance (상속)

### 1.1 상속이란?

- 한 클래스가 다른 클래스의 속성과 동작(메서드)을 물려받는 기능
- 코드 재사용, 유지보수성 향상, 다형성 구현에 사용
- `extends` 키워드로 상속

```java
class Parent {
    void sayHello() {
        System.out.println("Hello from Parent");
    }
}

class Child extends Parent {
    void sayHi() {
        System.out.println("Hi from Child");
    }
}
```

### 1.2 메서드 오버라이딩

- 자식 클래스가 부모 메서드를 재정의

```java
class Animal {
    void sound() {
        System.out.println("Generic sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Bark");
    }
}
```

### 1.3 생성자와 super()

- 자식 생성자에서는 부모 생성자가 먼저 호출되어야 함

```java
class Parent {
    Parent() {
        System.out.println("Parent 생성자");
    }
}

class Child extends Parent {
    Child() {
        super();
        System.out.println("Child 생성자");
    }
}
```

### 1.4 자바의 상속 특징 요약

| 항목 | 설명 |
|------|------|
| extends | 상속 선언 키워드 |
| super | 부모 멤버 호출/생성자 참조 |
| 다중 상속 | 클래스는 불가 (인터페이스만 가능) |
| 설계 원칙 | 진정한 is-a 관계일 때만 사용 |

## 2. 상속보다는 합성을 사용하라 (Favor Composition over Inheritance)

### 2.1 개념 요약

- 상속은 강한 결합을 초래할 수 있음
- 합성은 내부에 다른 객체를 포함하여 기능을 위임함 (has-a 관계)

### 2.2 예시 비교

#### 상속 사용

```java
class Animal {
    void move() {
        System.out.println("Moving...");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Bark!");
    }
}
```

#### 합성 사용

```java
class Mover {
    void move() {
        System.out.println("Moving...");
    }
}

class Dog {
    private Mover mover = new Mover();

    void bark() {
        System.out.println("Bark!");
    }

    void move() {
        mover.move();
    }
}
```

### 2.3 비교 표

| 항목 | 상속 | 합성 |
|------|------|------|
| 결합도 | 강함 | 느슨함 |
| 유연성 | 낮음 | 높음 |
| 재사용성 | 제한적 | 우수 |
| 테스트 용이성 | 낮음 | 높음 |
| 구조 | 수직 구조 | 평면 구조 |

### 2.4 언제 상속을 써도 되는가?

> 진정한 is-a 관계일 때만 상속을 사용하라.

| 예시 | 적절성 |
|------|--------|
| Circle extends Shape | 적절 |
| Dog extends Animal | 조건부 적절 (부모 클래스가 잘 추상화돼 있을 경우) |
| Button extends Window | 부적절한 상속 |

## 최종 요약

상속은 자바에서 중요한 개념이지만, 잘못 사용하면 유연성을 해치고 결합도를 높인다.  
따라서 기능 재사용이 목적이라면 합성(Composition)을 우선 고려하라.
