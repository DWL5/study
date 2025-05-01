# Java Interface (인터페이스) 정리

## 1. 인터페이스란?

- 인터페이스는 클래스가 따라야 할 **설계도**이다.
- 메서드의 시그니처(이름, 매개변수, 반환타입)만 정의하고,  
  실제 구현은 이를 `구현(implement)`하는 클래스가 책임진다.

```java
interface Animal {
    void makeSound();
}
```

---

## 2. 인터페이스 vs 클래스

| 항목 | 인터페이스 | 클래스 |
|------|------------|--------|
| 구현 여부 | 없음 (Java 8 이후 일부 가능) | 있음 |
| 다중 상속 | 가능 | 불가능 |
| 목적 | 기능 규칙 정의 | 기능 구현 |

---

## 3. 기본 사용 예시

```java
interface Animal {
    void makeSound();
}

class Dog implements Animal {
    public void makeSound() {
        System.out.println("멍멍");
    }
}
```

---

## 4. Java 8 이후 인터페이스의 변화

### 4.1 default 메서드 (Java 8)

```java
interface Greeting {
    default void sayHello() {
        System.out.println("안녕하세요!");
    }
}
```

### 4.2 static 메서드 (Java 8)

```java
interface MathUtils {
    static int square(int x) {
        return x * x;
    }
}

// 사용
int result = MathUtils.square(4); // 16
```

### 4.3 private 메서드 (Java 9)

```java
interface Logger {
    default void logInfo(String msg) {
        log("INFO", msg);
    }

    default void logError(String msg) {
        log("ERROR", msg);
    }

    private void log(String level, String msg) {
        System.out.println("[" + level + "] " + msg);
    }
}
```

| 기능 | Java 버전 | 설명 | 사용 위치 |
|------|------------|------|-----------|
| `default` | 8 | 인스턴스 메서드 기본 구현 | 클래스에서 override 가능 |
| `static`  | 8 | 정적 메서드 정의 | 인터페이스명으로 호출 |
| `private` | 9 | 내부 전용 로직 분리 | default/static 내부에서만 호출 가능 |

---

## 5. 인터페이스의 장점

| 장점 | 설명 |
|------|------|
| 느슨한 결합 | 구현체에 의존하지 않고 인터페이스에 의존 |
| 유연한 설계 | 다양한 구현을 자유롭게 교체 가능 |
| 다형성 지원 | 동일한 인터페이스 타입으로 여러 객체 처리 가능 |
| 테스트 용이 | 인터페이스만 있으면 Mock 객체를 통한 테스트 가능 |

---

## 6. 인터페이스 vs 추상 클래스

| 항목 | 인터페이스 | 추상 클래스 |
|------|-------------|--------------|
| 다중 상속 | 가능 | 불가능 |
| 필드 | 상수만 (`public static final`) | 인스턴스 변수 가능 |
| 생성자 | 없음 | 있음 |
| 목적 | 기능 정의 | 공통 로직 제공 및 확장 지점 설정 |
| 사용 예 | 전략 패턴, 의존성 주입 | 템플릿 메서드 패턴 등 |

---

## 요약

> 인터페이스는 클래스가 따라야 할 규칙(계약서)이며,  
> 다양한 구현체를 유연하게 교체하거나 확장할 수 있는 기반을 제공한다.  
> Java 8 이후로는 기본 구현도 가능하여, 설계 유연성이 더욱 강화되었다.
