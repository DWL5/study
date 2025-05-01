# Java Abstraction & Skeletal Implementation

## 1. 추상화 (Abstraction)

### 1.1 추상화란?

- 복잡한 시스템에서 **핵심적인 동작만 노출**하고, **세부 구현은 감추는 객체지향 설계 원칙**
- 사용자는 **무엇을 할 수 있는가**만 알고, **어떻게 동작하는지는 몰라도 되도록** 만든다

---

### 1.2 자바에서 추상화를 구현하는 방법

| 방법 | 설명 |
|------|------|
| 추상 클래스 (`abstract class`) | 공통 속성과 일부 구현을 가진 클래스 |
| 인터페이스 (`interface`) | 메서드 시그니처만 정의, 구현은 외부에 맡김 |

---

### 1.3 추상 클래스 예시

```java
abstract class Animal {
    abstract void makeSound();
    
    void eat() {
        System.out.println("먹는다");
    }
}
```

---

### 1.4 인터페이스 예시

```java
interface Vehicle {
    void move();
}

class Car implements Vehicle {
    public void move() {
        System.out.println("자동차가 움직인다");
    }
}
```

---

### 1.5 추상화의 장점

| 장점 | 설명 |
|------|------|
| 설계 명확성 | 클래스의 역할이 분명해짐 |
| 유연성 | 구현을 바꾸더라도 외부 영향 없음 |
| 다형성 | 다양한 구현체를 하나의 타입으로 다룰 수 있음 |
| 재사용성 | 공통 기능을 추상화해 재사용 가능 |

---

### 1.6 추상화 vs 캡슐화

| 항목 | 추상화 | 캡슐화 |
|------|--------|--------|
| 목적 | 기능 정의 | 내부 보호 |
| 수단 | 인터페이스, 추상 클래스 | 접근 제어자 (private 등) |
| 초점 | 외부에 무엇을 노출할지 | 내부 구현을 숨길지 |

---

## 2. Skeletal Implementation (추상 골격 구현 클래스)

### 2.1 개념

- 인터페이스를 구현하는 **추상 클래스**를 만들어,
  공통 코드를 제공하고, **핵심 메서드만 하위 클래스에서 구현**하게 하는 구조

---

### 2.2 대표 예시 - 자바 컬렉션

```java
public abstract class AbstractList<E> implements List<E> {
    public void add(E e) {
        add(size(), e);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract E get(int index);
    public abstract int size();
    public abstract void add(int index, E element);
}
```

```java
class MyList<E> extends AbstractList<E> {
    private ArrayList<E> internal = new ArrayList<>();

    public E get(int index) { return internal.get(index); }
    public int size() { return internal.size(); }
    public void add(int index, E element) { internal.add(index, element); }
}
```

---

### 2.3 언제 사용해야 할까?

| 상황 | 사용 권장 |
|------|-----------|
| 인터페이스 구현이 반복될 때 | ✅ |
| 공통 구현은 제공하되 일부만 override하게 하고 싶을 때 | ✅ |
| 템플릿 메서드 패턴처럼 흐름은 고정, 일부만 커스터마이징할 때 | ✅ |
| 외부 사용자에게 인터페이스와 기본 구현을 동시에 제공하고 싶을 때 | ✅ |

---

### 2.4 언제 사용하지 말아야 할까?

| 상황 | 사용 비권장 이유 |
|------|----------------|
| 구현체가 하나뿐일 때 | 굳이 분리할 필요 없음 |
| 공통 로직이 거의 없을 때 | 인터페이스만으로 충분 |
| 다중 클래스 상속이 필요한 구조 | 자바는 다중 클래스 상속을 지원하지 않음 |

---

## 3. 요약

- **추상화**는 객체가 수행할 수 있는 작업만 외부에 보여주는 설계 방식
- **Skeletal Implementation**은 추상화를 실용적으로 활용할 수 있는 대표 패턴
- 인터페이스 + 추상 클래스 구조는 재사용성과 유지보수성을 동시에 잡을 수 있는 강력한 조합
