# Java 타입 캐스팅 (Type Casting)

타입 캐스팅은 변수를 하나의 데이터 타입에서 다른 타입으로 변환하는 과정입니다.  
서로 다른 타입의 변수 간 연산을 하거나, 한 타입의 값을 다른 타입의 변수에 저장해야 할 때 자주 사용됩니다.

Java에서는 타입 캐스팅이 **묵시적(자동)**일 수도 있고, **명시적(형변환 연산자 필요)**일 수도 있습니다.

---

## 묵시적 캐스팅 (자동 변환, Widening)

- 컴파일러가 자동으로 처리
- 작은 타입 → 큰 타입으로 변환될 때 발생

### 예시

```java
int num = 100;
long bigNum = num; // int → long 자동 캐스팅
```

| 원래 타입 | 변환 가능 타입 |
|-----------|----------------|
| byte | short, int, long, float, double |
| short | int, long, float, double |
| char | int, long, float, double |
| int | long, float, double |
| long | float, double |
| float | double |

---

## 명시적 캐스팅 (강제 변환, Narrowing)

- `(타입)`을 이용해 직접 변환 지정
- 큰 타입 → 작은 타입으로 변환할 때 사용
- 데이터 손실이 발생할 수 있음

### 예시

```java
double price = 9.99;
int roundedPrice = (int) price; // double → int 명시적 캐스팅
```

---

## 요약

- **묵시적 캐스팅**: 자동 변환, 안전, 데이터 손실 없음
- **명시적 캐스팅**: 수동 변환, 데이터 손실 가능성 있음
- 변환 대상 타입의 범위와 정밀도를 고려해야 함
