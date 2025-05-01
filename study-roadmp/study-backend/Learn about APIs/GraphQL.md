# GraphQL이란?

## 개요

GraphQL은 Facebook이 개발한 API용 쿼리 언어이자,
그 쿼리를 실행하는 실행 환경 이다.

기존의 REST API는 고정된 엔드포인트(URL 경로)가 정해진 데이터만 반환하는 반면,
GraphQL은 클라이언트가 필요한 데이터만 정확하게 요청할 수 있도록 해준다.
이 덕분에 API 사용이 더 유연하고 효율적이다.

GraphQL은 하나의 단일 엔드포인트만 사용하며,
`데이터의 타입과 구조를 정의한 스키마(schema)`를 기반으로 작동한다.

이 방식은 REST에서 흔히 발생하는
오버페치(over-fetching), 언더페치(under-fetching) 문제를 줄여주며,
웹, 모바일 등 다양한 플랫폼에서 복잡한 데이터 요구가 있는 애플리케이션에 적합하다.

---

## 특징

- 하나의 엔드포인트 (`/graphql`)로 다양한 자원에 접근 가능
- 요청 시 데이터의 구조를 클라이언트가 직접 지정
- JSON 형식으로 응답

---

## 예시

```graphql
query {
  user(id: 1) {
    name
    email
    posts {
      title
      date
    }
  }
}
```

---

## 장점과 예시

| 장점 | 설명 | 예시 |
| --- | --- | --- |
| 정확한 데이터 요청 | 필요한 필드만 요청 가능 | 게시글 목록에서 제목만 필요한 경우 `posts { title }`만 요청 |
| 네트워크 효율성 | 오버페치, 언더페치 방지 | `/user`, `/user/posts` 따로 호출할 필요 없이 한 번에 처리 |
| 단일 요청으로 여러 자원 조회 | 복합 데이터 처리 용이 | 유저 정보와 해당 유저의 댓글을 한 번에 요청 가능 |
| API 버전 불필요 | 필드 단위로 스키마 진화 | `/v1`, `/v2` 같은 엔드포인트 없이도 대응 가능 |

---

## 단점과 예시

| 단점 | 설명 | 예시 |
| --- | --- | --- |
| 복잡한 쿼리 작성 가능성 | 클라이언트가 과도한 요청을 보낼 수 있음 | 중첩된 쿼리 요청이 과부하 유발 |
| 캐싱 어려움 | REST의 URL 기반 캐싱 어려움 | GET 요청 URL이 고정되지 않음 |
| 권한 관리 복잡 | 필드 단위 보안 제어 필요 | 특정 필드는 특정 사용자만 볼 수 있도록 제한 필요 |
| 러닝커브 존재 | 스키마와 타입 시스템 학습 필요 | 초보자에게 생소한 개념 존재 (Query, Mutation, Resolver 등)

---

## Spring Boot에서 GraphQL 사용 예시

### 1. 의존성 추가 (Gradle 기준)

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-graphql")
}
```

### 2. 스키마 파일 작성 (resources/graphql/user.graphqls)

```graphql
type Query {
  user(id: ID!): User
}

type User {
  id: ID!
  name: String
  email: String
}
```

### 3. Resolver 구현

```java
@Component
public class UserResolver {

    public User getUser(Long id) {
        return new User(id, "Alice", "alice@example.com");
    }
}
```

### 4. Query 실행 예시

```graphql
query {
  user(id: 1) {
    name
    email
  }
}
```

응답:
```json
{
  "data": {
    "user": {
      "name": "Alice",
      "email": "alice@example.com"
    }
  }
}
```

---

## 한 줄 요약

> GraphQL은 클라이언트가 원하는 데이터만 정확히 요청하고, 서버는 그 요청에 정확히 응답하는 구조의 유연한 API 쿼리 언어이다.
"""
