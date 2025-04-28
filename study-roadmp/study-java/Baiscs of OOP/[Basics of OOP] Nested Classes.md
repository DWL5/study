## 자바 중첩 클래스 (Nested Classes)

### 중첩 클래스란?
- 중첩 클래스 (Nested Class) = 클래스 안에 정의된 또 다른 클래스
- 외부 클래스 (Outer Class)안에 존재


## 종류

| 종류 | 설명 |
|------|------|
| 정적 중첩 클래스 (Static Nested Class) | static 키워드로 선언, 외부 클래스의 인스턴스 없이 사용 가능 |
| 내부 클래스 (Inner Class) | static 키워드가 없는 중첩클래스, 외부 클래스의 인스턴스에 종속됨 |


### 정적 중첩 클래스
```
public class Outer {
    static class StaticNested {
        void greet() {
            System.out.println("Hello from static nested class!");
        }
    }
}
Outer.StaticNested nested = new Outer.StaticNested();
nested.greet();
```

- 외부 인스턴스 필요없음
- 외부 클래스의 static 멤버만 직접 접근 가능

### 내부 클래스

```
public class Outer {
    private int value = 42;

    class Inner {
        void printValue() {
            System.out.println(value);
        }
    }
}
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.printValue();
```

- 외부 인스턴스 필요
- 외부 클래스의 private 멤버까지 접근가능

### 정적 중첩 클래스 vs 내부 클래스 비교

| 구분 | 정적 중첩 클래스 | 내부 클래스 |
|-----|----------------|------------|
| static 여부 | 있음 | 없음 |
| 외부 인스턴스 필요 | 필요 없음 | 필요 |
| 접근 가능 멤버 | static 멤버만 | static + 인스턴스 멤버 모두 |
| 주 사용 상황 | 외부와 느슨한 관계, 유틸리티 클래스 | 외부와 밀접한 관계, 한정적 사용 |
| 메모리 관리 | 독립적, 메모리 누수 위험 없음 | 외부 인스턴스 참조 -> 메모리 누수 가능성 있음


### 중첩 클래스를 사용하는 이유
- 캡슐화 강화 : 외부에서 사용할 필요 없는 클래스를 숨길 수 있음
- 코드 구조화 : 관련 클래스를 함께 묶어 가독성과 관리성 향상
- 성능 최적화 : 정적 중첩 클래스는 외부 인스턴스 참조가 없으므로 가볍다

### Effective Java - 중첩 클래스는 정적 멤버 클래스로 정의하자!
- 정적 멤버 클래스가 아닌 내부 클래스는 무조건 외부 클래스를 인스턴스화 하고 내부 클래스를 인스턴스 화 할 수 있어서 외부 인스턴스가 쓰이지 않더라도 만들어져야 한다. 
- 이는 메모리 낭비로 이루어지며 GC에서도 해당 외부클래스가 사용하는 걸로 인식하여 수거해가지 않는다.



