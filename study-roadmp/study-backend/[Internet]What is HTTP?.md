# HTTP (HyperText Transfer Protocol)

- 웹에서 클라이언트와 서버가 정보를 주고받는 통신 규칙

## HTTP 요청 (Request)

```
[요청라인] GET /index.html HTTP/1.1
[헤더]     Host: example.com
[본문]     (POST일 경우 데이터 포함)
```

주요 메서드

| 메서드 | 설명 |
| --- | --- |
| GET | 데이터 조회 요청 |
| POST | 데이터 전송 요청 |
| PUT | 전체 수정 |
| PATCH | 부분 수정 |
| DELETE | 삭제 요청 |

## HTTP 헤더

- 요청 (Request)나 응답 (Response)에서 부가적인 정보를 담는 곳

## HTTP 응답 (Response)

```
[응답라인] HTTP/1.1 200 OK
[헤더]     Content-Type: text/html
[본문]     <html>...</html>
```

주요 상태 코드

| 코드 | 의미 |
| --- | --- |
| 200 | 성공 |
| 301 | 영구 이동 (리다이렉트) |
| 400 | 잘못된 요청 |
| 401 | 인증 필요 |
| 403 | 접근 금지 |
| 404 | 페이지 없음 |
| 500 | 서버 에러 |
