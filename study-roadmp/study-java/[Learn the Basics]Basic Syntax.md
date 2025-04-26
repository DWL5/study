# Java Basic Syntax

## Variables

### Instance Variables
- 클래스 내부, 메서드 외부에 선언되며 **객체마다 따로 존재**하는 변수입니다.

### Class Variables
- `static` 키워드로 선언되며 **모든 객체가 공유**하는 클래스 수준의 변수입니다.

### Local Variables
- 메서드나 블록 내부에서 선언되며 **해당 블록 안에서만 사용 가능한 변수**입니다.

### Parameters
- 메서드 호출 시 전달되는 **입력값을 받기 위한 변수**입니다.

---

## Primitive Type

| Data Type | Default Value |
|-----------|----------------|
| byte      | 0              |
| short     | 0              |
| int       | 0              |
| long      | 0L             |
| float     | 0.0f           |
| double    | 0.0d           |
| boolean   | false          |
| char      | '\u0000'      |
| String    | null           |

---

## 클래스

\`\`\`java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
\`\`\`

---

## 변수 선언

\`\`\`java
int number = 10;
double pi = 3.14;
String name = "Dawon";
\`\`\`

---

## 제어문

### if / else

\`\`\`java
if (score > 90) {
    System.out.println("A");
} else {
    System.out.println("B");
}
\`\`\`

### switch

\`\`\`java
switch (grade) {
    case 'A':
        System.out.println("Excellent");
        break;
    default:
        System.out.println("Keep trying");
}
\`\`\`

### for

\`\`\`java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
\`\`\`

### while

\`\`\`java
while (condition) {
    // 반복 실행
}
\`\`\`

### do-while

\`\`\`java
do {
    // 반복 실행할 코드
} while (조건);
\`\`\`

---

## 배열

\`\`\`java
int[] numbers = {1, 2, 3, 4};
\`\`\`

---

## 메서드

\`\`\`java
public static int add(int a, int b) {
    return a + b;
}
\`\`\`