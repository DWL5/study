# Java 문자열(String)과 메서드

문자열(String)은 문자들의 나열로, 문장이나 단어처럼 **텍스트 데이터를 표현**할 때 사용됩니다.  
자바에서는 문자열을 `String` 클래스로 다루며, 다양한 **메서드(기능)**를 제공하여 문자열을 쉽게 조작할 수 있습니다.

---

## 문자열 생성

```java
String greeting = "Hello, world!";
```

---

## 주요 문자열 메서드

| 메서드 | 설명 | 예시 |
|--------|------|------|
| `length()` | 문자열 길이 반환 | `str.length()` → `11` |
| `toUpperCase()` | 대문자로 변환 | `"abc".toUpperCase()` → `"ABC"` |
| `toLowerCase()` | 소문자로 변환 | `"ABC".toLowerCase()` → `"abc"` |
| `charAt(int index)` | 특정 인덱스의 문자 반환 | `"Java".charAt(1)` → `'a'` |
| `substring(start, end)` | 부분 문자열 추출 | `"Hello".substring(0, 2)` → `"He"` |
| `contains("text")` | 특정 문자열 포함 여부 | `"hello".contains("he")` → `true` |
| `equals("text")` | 문자열 비교 | `"hi".equals("hi")` → `true` |

---

## 요약

- `String`은 텍스트 데이터를 표현하는 데 사용됨
- 다양한 메서드를 통해 문자열의 길이 확인, 대소문자 변환, 부분 추출 등이 가능함
- 메서드를 활용하면 텍스트 데이터를 효과적으로 처리할 수 있음
