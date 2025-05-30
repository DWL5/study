# OAuth란?

## 정의

`OAuth (Open Authorization)`는  
제3자 애플리케이션이 사용자의 비밀번호를 알지 않고도  
사용자의 정보를 안전하게 제공받을 수 있도록 해주는 **권한 위임 프로토콜**이다.

예를 들어, 어떤 앱에서 "구글 계정으로 로그인"을 선택하면  
그 앱은 사용자의 구글 계정 비밀번호를 몰라도 구글 프로필 정보에 접근할 수 있다.

---

## 왜 필요한가?

기존에는 서비스에 로그인하기 위해 **ID와 비밀번호를 직접 입력**해야 했고,  
이 정보가 노출되거나 오용될 가능성이 있었다.  

**OAuth는 이러한 보안 문제를 해결하면서**,  
사용자 계정을 제3자 서비스와 안전하게 연결할 수 있도록 해준다.

---

## OAuth 인증 흐름 (프론트에서 코드 수신, 백엔드에서 토큰 요청)

### 개요

이 구조는 OAuth 인증에서 가장 일반적인 방식으로,  
**프론트엔드가 인가 코드(authorization code)를 먼저 수신**하고,  
그 코드를 **백엔드로 전달하여 백엔드가 액세스 토큰을 요청**하는 방식이다.

---

### 전체 흐름 요약

1. 사용자가 앱(프론트)에서 "구글 로그인" 클릭
2. 프론트가 구글 OAuth 서버로 리다이렉트
3. 사용자가 로그인 및 동의
4. 구글이 `redirect_uri`로 **인가 코드(code)** 전달
5. 프론트가 받은 인가 코드를 **백엔드 API로 전송**
6. 백엔드는 이 코드를 사용해 구글에 **토큰 요청**
7. 구글은 백엔드에 **액세스 토큰 및 ID 토큰** 응답
8. 백엔드는 액세스 토큰을 사용하여 **사용자 정보 조회**
9. 사용자 정보로 로그인/회원가입 처리 후 **JWT 발급**
10. 백엔드가 **JWT를 프론트에 전달** → 인증 완료

---

## 흐름도

```plaintext
[프론트] "구글 로그인" 버튼 클릭
    ↓
[OAuth 서버] 로그인 & 동의 → 인가 코드 발급
    ↓
[프론트] 리다이렉트 URI에서 인가 코드 수신
    ↓
[프론트 → 백엔드] 인가 코드 전달 (API 호출)
    ↓
[백엔드] 인가 코드로 토큰 요청
    ↓
[OAuth 서버] 액세스 토큰 응답
    ↓
[백엔드] 사용자 정보 조회 → 로그인 처리 → JWT 생성
    ↓
[백엔드 → 프론트] JWT 응답
```

---

### 왜 이렇게 분리하는가?

- `client_secret` 같은 민감 정보는 **프론트에 노출되면 안 됨**
- **백엔드에서만 토큰 요청을 수행**함으로써 보안 강화
- JWT는 **백엔드에서 발급**하여 프론트는 사용자 상태만 유지

---

### 실제 예시 (토큰 요청 - 백엔드에서)

```http
POST https://oauth2.googleapis.com/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code
&code=프론트에서_받은_인가_코드
&client_id=클라이언트_ID
&client_secret=클라이언트_시크릿
&redirect_uri=등록된_리다이렉트_URI
```

---

## 요약

| 단계 | 담당 주체 | 설명 |
|------|-----------|------|
| 인가 코드 수신 | 프론트 | redirect_uri로 code 수신 |
| 토큰 요청 | 백엔드 | code로 액세스 토큰 요청 |
| 사용자 정보 조회 및 JWT 발급 | 백엔드 | 사용자 인증 및 세션 처리 |
| 최종 로그인 상태 유지 | 프론트 | JWT 저장 및 사용 |

---

## 주요 개념

| 용어 | 설명 |
|------|------|
| **리소스 소유자 (User)** | 서비스 사용자 |
| **클라이언트 (Client)** | 사용자 대신 API를 호출하려는 앱 |
| **인증 서버** | 사용자 인증 및 토큰 발급 담당 |
| **리소스 서버** | 보호된 자원(API)을 제공하는 서버 |
| **액세스 토큰** | 자원 접근 권한을 부여하는 토큰 |
| **리프레시 토큰** | 액세스 토큰이 만료되었을 때 새로 발급받는 토큰 |

---

## 장점

- 사용자 비밀번호를 제3자 서비스에 노출하지 않음
- 세분화된 권한 제어 가능 (예: 이메일만 허용, 읽기 전용 등)
- 다양한 서비스와 안전한 인증 연동 가능

---

## 단점

- 구현이 상대적으로 복잡함
- 액세스 토큰이 유출되면 보안 위협 발생
- 토큰 저장과 갱신, 만료 처리 로직 필요

---

## 한 줄 요약

> OAuth는 사용자의 비밀번호 없이 제3자 애플리케이션이  
> 안전하게 사용자 자원에 접근할 수 있도록 해주는 **권한 위임 방식**이다.
