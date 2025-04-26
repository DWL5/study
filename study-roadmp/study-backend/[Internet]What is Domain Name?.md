### Domain Name이란?

- 도메인 이름은 웹사이트 주소를 쉽게 기억하고 접근할 수 있도록 만든 사람이 읽을 수 있는 주소.

### 왜 필요할까?
- 컴퓨터는 IP 주소로 통신한다.
  - 그러나 IP 주소는 사람이 외우기 어렵다.
- 그래서 도메인 이름이라는 문자 주소를 만들어 쉽게 사용할 수 있게 했다.

### 도메인 이름과 DNS의 관계
- 브라우저는 [naver.com](http://naver.com)과 같은 도메인 이름을 사용한다.
- DNS가 도메인 이름을 IP 주소로 변환해 통신이 가능하게 해준다.

### 도메인의 구조

| 부분 | 설명 |
| --- | --- |
| www | 서브도메인 (선택) |
| example | 도메인 이름 (내가 정한 이름), 세컨드 레벨 도메인 |
| .com | 최상위 도메인 (TLD, Top Level Domain) |

### 도메인 레코드
- 도메인은 사람이 기억하기 쉬운 주소이다.
- DNS 레코드는 이 도메인이 실제 어떤 IP나 다른 주소로 연결되는지를 설정하는 정보이다.

| 레코드 타입 | 설명 | 예시 |
| --- | --- | --- |
| A 레코드 | 도메인을 IP 주소에 연결 | [example.com](http://example.com/) → 123.45.67.89 |
| CNAME | 도메인을 다른 도메인 이름에 연결 | [www.example.com](http://www.example.com/) → [example.com](http://example.com/) |
| MX | 이메일 서버 주소 설정 | [example.com](http://example.com/) → [mail.example.com](http://mail.example.com/) |
| AAAA | A 레코드의 IPv6 버전 | IPv6 주소 연결 |

### Domain Name VS URL
- **Domain Name**
  - 웹 사이트의 주소 이름
- **URL**
  - 웹 자원의 전체 경로 (주소 + 상세 위치)