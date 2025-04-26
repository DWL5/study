# Java final 키워드

`final` 키워드는 변수, 메서드, 클래스에 **제약(제한)**을 걸기 위한 **비접근 제어자(non-access modifier)**입니다.

---

## final 변수

- 초기화 이후 값을 변경할 수 없습니다.
- 상수를 만들 때 사용합니다.

```java
final int MAX_VALUE = 100;
```

- 한번 초기화하면 다시 대입할 수 없습니다.

---

## final 메서드

- 서브클래스(자식 클래스)에서 **오버라이딩(재정의)** 할 수 없습니다.

```java
public class Parent {
    public final void show() {
        System.out.println("Cannot be overridden");
    }
}
```

---

## final 클래스

- 서브클래스를 만들 수 없습니다.
- 즉, **상속 금지**를 의미합니다.

```java
public final class Utility {
    // 이 클래스는 상속될 수 없습니다.
}
```

---

## final 키워드 사용의 이점

- **불변성 보장**: 값을 변경할 수 없으므로 데이터의 신뢰성을 높일 수 있습니다.
- **가독성 향상**: 코드 작성자가 해당 값이나 메서드, 클래스를 변경하지 않기를 의도했다는 것을 명확히 표현할 수 있습니다.
- **멀티스레드 환경에서 안전성**: final 변수는 한 번만 초기화되기 때문에, 여러 스레드가 동시에 접근해도 값이 변하지 않아 **동기화(synchronization) 문제를 줄일 수 있습니다.**
- **최적화 유리**: 컴파일러나 JVM이 final을 이용해 코드를 더 빠르고 효율적으로 최적화할 수 있습니다.

---

## 최적화 예시

### final 변수 최적화

```java
final int x = 10;
int result = x + 5;
```

- 컴파일러는 `x`가 절대 변하지 않는 걸 알기 때문에
- 컴파일 시점에 `result = 15;`로 미리 계산해 둘 수 있어
- 실행 시 추가 계산 없이 바로 결과 사용

---

### final 메서드 최적화

```java
class Parent {
    public final void show() {
        System.out.println("Hello");
    }
}
```

- final 메서드는 오버라이딩이 불가능하기 때문에
- JVM은 가상 호출(Virtual Call) 대신 직접 호출(Direct Call)로 최적화할 수 있어
- 메서드 호출이 더 빠름

---

### final 클래스 최적화

```java
public final class MathUtil {
    public static int add(int a, int b) {
        return a + b;
    }
}
```

- final 클래스를 상속할 수 없기 때문에
- JVM은 객체 메모리 레이아웃을 더 단순하게 구성할 수 있음

---

## 요약

- **final 변수**: 값 변경 불가 (상수)
- **final 메서드**: 오버라이딩 금지
- **final 클래스**: 상속 금지
- **장점**: 불변성, 가독성 향상, 멀티스레드 안전성, 컴파일/실행 최적화 가능
