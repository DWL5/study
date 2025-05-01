# Java Static vs Dynamic Binding

## 1. 바인딩(Binding)이란?

- **메서드 호출이 실제 어떤 메서드와 연결될지를 결정하는 과정**
- 이 바인딩이 **컴파일 시점**에 일어나면 정적 바인딩 (Static Binding),  
  **실행 시점**에 일어나면 동적 바인딩 (Dynamic Binding)

---

## 2. Static Binding (정적 바인딩)

### 정의

- 컴파일 타임에 메서드 호출이 어떤 메서드에 연결될지 결정됨

### 대상

- `static`, `final`, `private` 메서드
- 메서드 **오버로딩(Overloading)**

### 예시

```java
class Calculator {
    static void greet() {
        System.out.println("Hello");
    }

    void print(int x) {
        System.out.println("정수: " + x);
    }

    void print(String x) {
        System.out.println("문자열: " + x);
    }
}
```

---

## 3. Dynamic Binding (동적 바인딩)

### 정의

- 런타임(실행 시점)에 어떤 메서드를 실행할지 결정됨

### 대상

- **오버라이딩된 인스턴스 메서드**

### 예시

```java
class Animal {
    void sound() {
        System.out.println("동물 소리");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("멍멍");
    }
}

Animal a = new Dog();
a.sound(); // Dog의 sound() 실행됨 → 동적 바인딩
```

---

## 4. 차이점 요약

| 항목 | Static Binding | Dynamic Binding |
|------|----------------|-----------------|
| 결정 시점 | 컴파일 시 | 런타임 시 |
| 적용 대상 | static, final, private, 오버로딩 | 오버라이딩된 인스턴스 메서드 |
| 다형성 | 적용 안 됨 | 적용됨 |
| 성능 | 빠름 (최적화 용이) | 상대적으로 느림 |

---

## 5. 왜 중요한가?

### 1. 다형성(Polymorphism)을 이해하려면

```java
Animal a = new Dog();
a.sound(); // Dog의 sound() 실행됨
```

- 타입은 Animal이지만 객체는 Dog → 실제 메서드는 Dog 기준으로 실행됨  
→ 동적 바인딩 덕분에 다형성이 작동함

---

### 2. 성능 판단에 영향

- Static Binding은 컴파일 시점에 결정되므로 더 빠르고 최적화에 유리함  
- Dynamic Binding은 유연하지만 런타임 비용이 발생함

---

### 3. 디버깅과 예측

- 어떤 클래스의 어떤 메서드가 호출될지 예측할 수 있어야 디버깅이 쉬움  
- 동적 바인딩에서는 런타임 결과를 잘 이해해야 함

---

### 4. 설계와 패턴

- 인터페이스 기반 설계, 전략 패턴, 템플릿 메서드 패턴 등은 모두  
  동적 바인딩을 전제로 동작

---

## ✅ 한 줄 요약

> 정적 바인딩은 **컴파일 시점**에,  
> 동적 바인딩은 **실행 시점**에 어떤 메서드가 호출될지를 결정한다.  
> 이 차이를 이해해야 자바의 다형성, 성능, 설계, 디버깅까지 제대로 활용할 수 있다.
