# JWT란?

## 정의

**JWT (JSON Web Token)**는  
서버와 클라이언트 간에 정보를 안전하게 전달하고 인증하는 데 사용되는 토큰 기반 인증 방식이다.

> 인증 후 발급된 토큰을 클라이언트가 저장하고,  
> 이후 요청 시 토큰을 함께 보내면 서버는 이를 검증해 사용자를 식별한다.

---

## 구조

JWT는 총 3개의 부분으로 구성되며, `.`으로 구분된다:

```
헤더(Header).페이로드(Payload).서명(Signature)
```

예시:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiIxMjM0NTYiLCJuYW1lIjoi홍길동IiwiaWF0IjoxNjE2MjM5MDIyfQ.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

| 구성 요소 | 설명 |
| --- | --- |
| Header | 사용할 알고리즘 (예: HS256)과 토큰 유형 명시 |
| Payload | 사용자 정보, 권한, 만료 시간 등의 데이터 포함 |
| Signature | 위 두 부분을 비밀 키로 서명한 값 (변조 방지용) |

---

## 사용 흐름 예시

1. 사용자가 로그인
2. 서버가 사용자 정보를 기반으로 JWT 발급
3. 클라이언트가 JWT를 저장 (localStorage, cookie 등)
4. 이후 요청 시 JWT를 함께 전송

HTTP 요청 예시:
```
Authorization: Bearer <JWT 토큰>
```

---

## 장점

- 무상태(Stateless) 인증 가능 → 서버가 세션을 관리하지 않아도 됨
- 토큰에 정보가 담겨 있어 별도 DB 조회 없이 인증 가능
- 다양한 시스템 간 인증 공유에 적합 (마이크로서비스, 모바일 등)

---

## 단점

- 탈취되면 유효 시간 동안 악용 가능 → HTTPS 사용 필수
- 서버 측에서 즉시 무효화 어렵다 (로그아웃 처리 등 복잡)
- 토큰 크기가 커지면 네트워크 오버헤드 발생 가능성 있음

---

## 한 줄 요약

JWT는 클라이언트와 서버 간 인증 정보를 토큰 형태로 안전하게 주고받으며,  
무상태 인증을 가능하게 해주는 유연하고 강력한 방식이다.
