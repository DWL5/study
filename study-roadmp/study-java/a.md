# Pass by Value vs Pass by Reference 정리

`Pass by value(값에 의한 전달)`과 ass by `reference(참조에 의한 전달)`은
함수나 메서드에 인자를 전달하는 두 가지 방식이다.

- Pass by value에서는 변수의 값을 복사한 것이 함수로 전달되기 때문에, 함수 내부에서 해당 매개변수를 변경하더라도 원래 변수에는 영향을 주지 않는다.
- 반면에 Pass by reference에서는 변수에 대한 `직접적인 참조(주소)`가 전달되므로, 함수 내부에서 매개변수를 변경하면 원래 변수도 직접적으로 영향을 받는다.

간단히 말해 값만 복사해서 전달하면 원본은 안전하고, 참조를 넘기면 원본까지 바뀔 수 있다는 뜻

## 1. 기본 개념

| 개념 | 설명 |
|------|------|
| Pass by Value | 값을 복사해서 전달. 함수 안에서 원본 값은 영향을 받지 않음 |
| Pass by Reference | 변수의 주소(참조)를 전달. 함수 안에서 원본 값도 변경 가능 |

---

## 2. 자바(Java)는 어떤 방식?

자바는 항상 Pass by Value만 지원한다.

- 기본형 (int, double 등): 값 자체를 복사
- 참조형 (객체 등): 참조값(주소)을 복사해서 전달

```java
void change(Person p) {
    p.name = "Bob";
}
```

- 여기서 `p`는 참조값을 복사해서 받음
- 그 참조로 객체 내부 값을 바꿀 수 있음
- 하지만 `p = new Person()`처럼 참조 자체를 바꿔도 원래 객체에는 영향 없음

---

## 3. C++과 비교

### C++: Pass by Reference 지원

```cpp
class Person {
public:
    std::string name;
    Person(std::string n) : name(n) {}
};

void changeName(Person& p) {
    p.name = "Bob";
}
```

```cpp
Person person("Alice");
changeName(person);
std::cout << person.name;  // Bob
```

- `Person& p`는 참조 전달이므로 원본 객체의 name이 바뀐다

### Java: 객체 예시

```java
class Person {
    String name;
    Person(String name) {
        this.name = name;
    }
}

void changeName(Person p) {
    p.name = "Bob";
}
```

```java
Person person = new Person("Alice");
changeName(person);
System.out.println(person.name);  // Bob
```

- 자바는 참조값(주소)을 복사해서 전달하지만, 그 주소를 따라가서 객체 내부를 수정할 수 있음

---

## 4. 참조 자체 바꾸기 비교

### C++ (참조 바꾸기 가능)

```cpp
void reassign(Person& p) {
    p = Person("Charlie"); // person 자체가 새 객체로 바뀜
}
```

### Java (참조 바꾸기 불가능)

```java
void reassign(Person p) {
    p = new Person("Charlie"); // 복사된 참조만 바뀜, 원본은 그대로
}
```

---

## 5. 정리 표

| 비교 항목 | C++ (Call by Reference) | Java (Call by Value) |
|-----------|--------------------------|-----------------------|
| 기본형 수정 | 가능 | 복사본만 수정 |
| 객체 필드 수정 | 가능 | 가능 |
| 객체 자체 변경 | 가능 | 불가능 (참조값 복사) |
| 참조 자체 전달 | 가능 (& 사용) | 불가능 |

---

## 6. 한 줄 요약

자바는 항상 Pass by Value이다. 객체도 참조값을 값으로 복사해서 넘길 뿐이며,  
참조 자체를 바꾸는 건 불가능하다.  
C++처럼 변수 그 자체를 넘기는 진짜 Pass by Reference는 자바엔 없다.
