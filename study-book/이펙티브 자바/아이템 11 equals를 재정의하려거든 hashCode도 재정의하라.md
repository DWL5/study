# 아이템 11: equals를 재정의하려거든 hashCode도 재정의하라

**요약**

- equals를 재정의하면 반드시 hashCode도 재정의해야 한다.
- 이 둘의 일관성이 깨지면 **HashMap**, **HashSet**, **Hashtable**과 같은 컬렉션에서 객체를 정상적으로 사용할 수 없다.

**hashCode와 equals의 관계**

- **equals가 true**인 두 객체는 반드시 **같은 hashCode**를 가져야 한다.
    - 즉, x.equals(y) == true라면 x.hashCode() == y.hashCode()도 **항상 true**여야 한다.
    - 하지만 **hashCode가 같다고 해서 equals가 true인 것은 아니다**.
    - 즉, 서로 다른 객체라도 **같은 hashCode**를 가질 수 있다(해시 충돌).

**왜 hashCode를 재정의해야 하는가?**

- **Hash 기반 컬렉션의 동작 원리**
    - HashMap, HashSet 등은 객체를 `해시값(hashCode)`을 기반으로 저장한다.
    - hashCode를 통해 객체를 빠르게 찾거나 비교한다.
    - 만약 hashCode와 equals가 일관성이 없으면, 동일한 객체라도 **올바르게 검색, 저장, 삭제되지 않을 수 있음**.
- **hashCode를 재정의하지 않으면 발생하는 문제**
    - 기본 Object.hashCode()는 객체의 메모리 주소를 기반으로 생성되므로, **내용이 같은 객체라도 서로 다른 hashCode를 가질 수 있음.**
    - equals를 재정의하면서 hashCode를 재정의하지 않으면, **동일한 객체라도 Hash 컬렉션에서 올바르게 동작하지 않음.**

**예시: hashCode를 재정의하지 않았을 때의 문제**

```java
import java.util.HashSet;

public class HashCodeExample {
    public static void main(String[] args) {
        Person person1 = new Person("John", 25);
        Person person2 = new Person("John", 25);

        HashSet<Person> set = new HashSet<>();
        set.add(person1);

        System.out.println(set.contains(person2)); // 예상: true, 실제: false
    }
}

class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;

        Person other = (Person) obj;
        return name.equals(other.name) && age == other.age;
    }

    // hashCode를 재정의하지 않음
}
```

**문제 설명:**

- person1.equals(person2)는 **true**이지만, person1과 person2는 **다른 hashCode**를 가짐.
- HashSet은 내부적으로 hashCode를 비교하므로, set.contains(person2)가 **false**를 반환.

**hashCode 재정의하기**

- hashCode를 재정의할 때는 equals와의 일관성을 유지해야 하며, 가능하면 **모든 중요한 필드**를 포함하도록 구현해야 한다.

**예시: hashCode를 올바르게 재정의**

```java
import java.util.Objects;

class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;

        Person other = (Person) obj;
        return name.equals(other.name) && age == other.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age); // 주요 필드를 기반으로 해시값 생성
    }
}
```

```java
HashSet<Person> set = new HashSet<>();
set.add(person1);

System.out.println(set.contains(person2)); // true
```

**hashCode 재정의 방법**

- Objects.hash **사용 (권장)**
    - 간단히 주요 필드를 사용해 일관된 해시값을 생성:

```java
@Override

public int hashCode() {

return Objects.hash(name, age);

}
```

- **직접 해시값 계산 (성능 최적화 필요 시)**
    - 해시 충돌을 줄이고 성능을 높이기 위해 주요 필드로 직접 계산:

```java
@Override

public int hashCode() {

int result = name.hashCode();

result = 31 * result + Integer.hashCode(age);

return result;

}
```

**hashCode 재정의의 주의점**

- equals**와 일관성 유지**
    - equals에서 비교에 사용되지 않는 필드는 hashCode 계산에도 포함하지 않는다.
- **모든 필드를 포함할 필요는 없다**
    - 변경되지 않는 필드나 중요한 필드만 포함해도 충분하다.
        - 예: 대규모 배열이나 컬렉션 필드는 제외할 수 있음.
- **객체가 변경 가능한 경우 주의**
    - hashCode에 사용된 필드가 변경되면, 컬렉션 내 객체의 위치가 잘못될 수 있음.

**결론**

- equals를 재정의하면 반드시 hashCode도 재정의해야 한다.
- hashCode는 중요한 필드로 구성하며, Objects.hash를 사용하면 안전하고 간단하게 구현할 수 있다.
- equals와 hashCode의 일관성은 Hash 기반 컬렉션의 정상적인 동작을 보장한다.
