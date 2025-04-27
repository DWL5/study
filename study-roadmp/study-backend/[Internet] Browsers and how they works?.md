# Browsers and How They Work

## 브라우저(Browser)란?

- 브라우저는 인터넷에 있는 웹사이트를 사용자가 볼 수 있도록 해주는 프로그램이다.
- 대표적인 브라우저: Chrome, Safari, Edge, Firefox 등

> 쉽게 말하면, "인터넷의 문서를 찾아오고 보여주는 도구"이다.

---

## 브라우저의 주요 역할

- 사용자가 입력한 URL을 기반으로 웹페이지를 찾아온다.
- 웹페이지의 코드(HTML, CSS, JavaScript)를 해석해서 화면에 보여준다.
- 사용자와 서버 사이에 요청/응답을 중계해준다.

---

## 브라우저가 작동하는 흐름

1. **URL 입력**
   - 사용자가 주소창에 `www.example.com` 입력

2. **DNS 질의**
   - 입력한 도메인 이름을 IP 주소로 변환 (DNS 요청)

3. **서버에 요청 보내기**
   - 해당 IP 주소로 HTTP/HTTPS 요청을 보낸다.
   - 예: "index.html 파일 주세요!"

4. **서버 응답 수신**
   - 서버가 HTML, CSS, JavaScript 파일을 보내준다.

5. **렌더링(Rendering)**
   - 받은 HTML 파일을 분석하고,
   - CSS를 적용해서 스타일을 입히고,
   - JavaScript를 실행해서 동작을 추가한다.
   - 최종적으로 화면에 웹페이지를 표시한다.

---

## 브라우저의 주요 구성 요소

| 구성 요소 | 역할 |
| --- | --- |
| User Interface (UI) | 주소창, 뒤로 가기 버튼, 즐겨찾기 등 사용자와 상호작용하는 부분 |
| Browser Engine | UI와 렌더링 엔진 사이를 연결하고 관리 |
| Rendering Engine | HTML, CSS를 해석해서 화면에 웹페이지를 그림 |
| Networking | HTTP 요청을 보내고 응답을 받음 (네트워크 통신) |
| JavaScript Engine | JavaScript 코드를 실행 |
| Data Storage | 쿠키, 로컬 스토리지, 캐시 데이터 등을 저장 |

---

## 예시 흐름 (www.google.com 접속)

- 사용자가 브라우저에 `www.google.com` 입력
- 브라우저가 DNS 서버에 요청하여 IP 주소를 획득
- 브라우저가 구글 서버로 HTTP 요청을 보냄
- 구글 서버가 HTML 파일을 응답
- 브라우저가 HTML 파싱
- 필요한 CSS, JavaScript 파일 추가 요청
- 브라우저가 렌더링 엔진을 통해 화면에 최종 웹페이지를 그림

---
