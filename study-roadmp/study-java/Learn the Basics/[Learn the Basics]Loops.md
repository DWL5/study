# Java 반복문 (Loops)

반복문은 코드의 일부분을 여러 번 실행할 때 사용합니다.  
자바에서는 `for`, `for-each`, `while`, `do...while`의 네 가지 반복문이 제공됩니다.

---

## for 문

초기화, 조건식, 증감식을 사용하여 반복을 제어합니다.

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

---

## for-each 문

배열이나 컬렉션의 요소를 순회할 때 사용합니다.

```java
for (String name : names) {
    System.out.println(name);
}
```

---

## while 문

조건이 참일 동안 반복 실행합니다.

```java
int i = 0;
while (i < 5) {
    System.out.println(i);
    i++;
}
```

---

## do...while 문

코드를 먼저 실행한 뒤 조건을 검사합니다 (최소 1회 실행 보장).

```java
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

---

## 요약

- `for`: 반복 횟수가 명확할 때
- `for-each`: 배열/컬렉션 순회에 적합
- `while`: 조건에 따라 반복
- `do...while`: 최소 한 번은 실행
