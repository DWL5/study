# Java 조건문 (Conditionals)

조건문은 조건에 따라 코드의 실행 흐름을 제어하는 구조입니다.  
조건이 참인지 거짓인지에 따라 실행할 코드를 분기할 수 있습니다.

---

## if

조건이 참이면 해당 블록을 실행합니다.

```java
if (score > 90) {
    System.out.println("A");
}
```

---

## else

if 조건이 거짓일 경우 실행됩니다.

```java
if (score > 90) {
    System.out.println("A");
} else {
    System.out.println("B or below");
}
```

---

## else if

여러 조건을 순차적으로 검사할 때 사용합니다.

```java
if (score >= 90) {
    System.out.println("A");
} else if (score >= 80) {
    System.out.println("B");
} else {
    System.out.println("C or below");
}
```

---

## switch

다양한 경우에 따라 분기할 때 사용합니다.

```java
switch (grade) {
    case 'A':
        System.out.println("Excellent");
        break;
    case 'B':
        System.out.println("Good");
        break;
    default:
        System.out.println("Keep trying");
}
```

---

## 삼항 연산자 (?:)

간단한 조건을 한 줄로 처리할 때 사용합니다.

```java
String result = (score >= 60) ? "Pass" : "Fail";
```

---

## 요약

- `if`, `else`, `else if`는 조건 흐름을 단계별로 제어
- `switch`는 여러 경우 중 하나를 선택
- `?:`는 짧은 조건 표현에 적합
