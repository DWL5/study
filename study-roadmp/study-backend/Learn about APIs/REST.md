# REST란?

## REST (Representational State Transfer)

- 웹의 자원을 URI로 표현하고, HTTP 메서드(GET, POST, PUT, DELETE 등)를 이용해 자원을 조작하는 아키텍처 스타일이다.
- 웹을 더 단순하고 일관성 있게 만들기 위해 고안된 설계 방법이다.

---

## REST와 HTTP의 연관성

- **HTTP는 통신 프로토콜**, **REST는 아키텍처 스타일**이다.
- REST는 HTTP의 특징을 자연스럽게 활용하여 설계된다.
- HTTP의 메서드(GET, POST, PUT, DELETE 등)를 자원 조작(CRUD)에 대응시켜 사용한다.
- HTTP의 URI, 헤더, 응답 코드, 캐시 기능 등을 적극 활용한다.

| HTTP 요소 | REST에서의 사용 |
| --- | --- |
| HTTP 메서드 | CRUD 작업 (GET: 조회, POST: 생성, PUT: 수정, DELETE: 삭제) |
| URI | 자원(Resource)을 식별하는 주소 |
| 상태 코드(Status Code) | 요청 결과를 의미하는 표준 코드 전달 |
| 헤더(Header) | 요청/응답 메타데이터 전달 |
| 캐시(Cache) | 서버 부하 감소 및 응답 속도 향상 |

---

## REST API 기본 규칙

| HTTP 메서드 | 의미 | 예시 (users라는 리소스) |
| --- | --- | --- |
| GET | 자원 조회 | `GET /users/1` |
| POST | 자원 생성 | `POST /users` |
| PUT | 자원 전체 수정 | `PUT /users/1` |
| PATCH | 자원 부분 수정 | `PATCH /users/1` |
| DELETE | 자원 삭제 | `DELETE /users/1` |

---

# REST API를 잘 설계하는 추가 규칙

## 1. URI는 명사를 사용한다

- 동사가 아니라 **자원(명사)**를 중심으로 URI를 설계한다.
- 예시
  - 잘못된 예: `/getUser`, `/createUser`
  - 올바른 예: `/users`, `/users/{id}`

## 2. 슬래시는 계층 구조를 표현할 때만 사용한다

- 자원의 포함 관계를 표현할 때만 슬래시(`/`)를 사용한다.
- 예시
  - `/users/1/orders` → 사용자 1번의 주문 목록

## 3. 복수형 명사를 사용한다

- 일관성을 위해 자원 이름은 복수형으로 작성한다.
- 예시
  - `/users`, `/articles`, `/products`

## 4. 상태 변경은 적절한 HTTP 메서드를 사용한다

- GET 요청에서는 절대 서버 데이터를 변경하지 않는다.
- 데이터 변경은 POST, PUT, PATCH, DELETE를 사용한다.

## 5. HTTP 상태 코드를 명확하게 반환한다

| 상태 코드 | 의미 |
| --- | --- |
| 200 OK | 요청 성공 (조회) |
| 201 Created | 자원 생성 성공 |
| 204 No Content | 요청 성공했지만 반환할 데이터 없음 |
| 400 Bad Request | 잘못된 요청 |
| 401 Unauthorized | 인증 실패 |
| 403 Forbidden | 권한 없음 |
| 404 Not Found | 자원 없음 |
| 500 Internal Server Error | 서버 오류 |


# URI란?

## URI (Uniform Resource Identifier)

- URI는 웹 상의 자원(Resource)을 식별하거나 위치를 지정하기 위한 표준 형식의 문자열이다.
- 쉽게 말하면, "인터넷에서 어떤 것을 가리키는 주소"이다.

---

## URI의 기본 역할

- "이것이 무엇인지" 식별
- "어디서 찾을 수 있는지" 위치 지정

둘 중 하나 또는 둘 다 수행할 수 있다.

---

## URI의 구성

URI는 다음과 같은 구조를 가진다:

프로토콜://호스트/경로?쿼리#프래그먼트

예를 들어, 다음과 같은 형태를 가진다:

https://www.example.com/articles?id=5#section2

| 구성 요소 | 설명 |
| --- | --- |
| 프로토콜 (Scheme) | 어떤 프로토콜로 접근할지 명시 (예: http, https, ftp) |
| 호스트 (Host) | 서버의 주소 (예: www.example.com) |
| 경로 (Path) | 서버 내 자원의 위치 (예: /articles, /users/1) |
| 쿼리 (Query) | 추가적인 요청 파라미터 (예: ?id=5) |
| 프래그먼트 (Fragment) | 문서 내 특정 위치를 가리킴 (예: #section2) |

---

## URI, URL, URN의 관계

| 용어 | 설명 | 예시 |
| --- | --- | --- |
| URI | 자원을 식별하는 모든 방법의 총칭 | URL, URN 둘 다 포함 |
| URL | 자원의 위치(Location)를 나타냄 | https://www.example.com/page |
| URN | 자원의 이름(Name)을 나타냄 | urn:isbn:978-3-16-148410-0 |

- **URI는 URL과 URN을 모두 포함하는 상위 개념**이다.

---

## 정리

> URI는 인터넷에 존재하는 자원을 식별하거나 위치를 지정하는 표준 주소 체계이며,  
> REST API에서는 자원을 명확하게 식별하는 핵심 역할을 한다.
