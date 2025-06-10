# 웹 서버 소프트웨어(Apache, Nginx)의 서버 간 라우팅 기능은 OSI 7계층 중 어디서 작동하는지 설명해보세요.

Apache나 Nginx가 제공하는 리버스 프록시/로드밸런싱(서버 간 라우팅) 기능은 주로 **애플리케이션 계층(Layer 7)**에서 동작합니다. 다만, 설정에 따라 **전송 계층(Layer 4)**에서도 동작할 수 있습니다.

- 일반적인 웹 서버 라우팅 → Layer 7
- 경량·비(非)HTTP 프로토콜 로드밸런싱 → Layer 4

## 애플리케이션 계층 (Layer 7) – HTTP(S) 레벨 라우팅

- 모듈/기능
  - Nginx: http { … proxy_pass … }, upstream 블록에서 호스트별·경로별 분기
  - Apache: mod_proxy_http, ProxyPass / ProxyPassMatch, mod_proxy_balancer
- 특징
  - HTTP 헤더(Host, Path, Cookie 등)를 파싱해서 “어떤 요청을 어떤 백엔드 서버”로 보낼지 결정
  - URL 경로별, 도메인별, 헤더 조건별 상세한 룰 작성 가능
  - HTTPS 종료(SSL offload) → 내부 통신은 HTTP로 프록시
- 장점
  - 요청의 내용까지 분석해서 정교한 분기
  - A/B 테스트, Canary 배포, 세션 쿠키 기반 스티키 세션 구현

## 전송 계층 (Layer 4) – TCP/UDP 레벨 라우팅
- 모듈/기능
  - Nginx: stream { … proxy_pass … } (TCP/UDP 프록시)
  - Apache: 주로 Layer 7 위주, 순수 TCP 로드밸런싱은 외부 LB 사용 권장
- 특징
  - IP와 포트 정보만 보고 패킷 레벨에서 백엔드로 전달
  - 요청 내용을 해석하지 않고 커넥션 단위로 분산
- 장점
  - 오버헤드 적고 설정이 단순
  - HTTP 외에 데이터베이스, SMTP 같은 TCP 서비스도 로드밸런싱 가능

## 애플리케이션 계층 (Layer 7) Nginx 예시
```
http {
    include       mime.types;
    default_type  application/octet-stream;

    # --- HTTP 서버 블록 (포트 80)
    server {
        listen       80;
        server_name  example.com www.example.com;

        # 정적 파일
        root   /var/www/html;
        index  index.html index.htm;

        # 리버스 프록시 (API 요청)
        location /api/ {
            proxy_pass         http://backend;
            proxy_set_header   Host $host;
            proxy_set_header   X-Real-IP $remote_addr;
        }

        # HTTPS로 리디렉션
        return 301 https://$host$request_uri;
    }

    # --- HTTPS 서버 블록 (포트 443)
    server {
        listen       443 ssl http2;
        server_name  example.com www.example.com;

        ssl_certificate      /etc/ssl/certs/example.crt;
        ssl_certificate_key  /etc/ssl/private/example.key;
        ssl_protocols        TLSv1.2 TLSv1.3;
        ssl_ciphers          HIGH:!aNULL:!MD5;

        root   /var/www/html;
        index  index.html index.htm;

        location /api/ {
            proxy_pass         http://backend;
            proxy_set_header   Host $host;
            proxy_set_header   X-Real-IP $remote_addr;
        }
    }

    # 업스트림 정의 (7계층 백엔드)
    upstream backend {
        server 127.0.0.1:8080;
        server 127.0.0.1:8081;
    }
}
```

## 애플리케이션 계층 (Layer 4) Nginx 예시
```
# stream 섹션 활성화 필요 (nginx.conf 최상단에)
stream {
    # --- TCP 로드밸런싱 (예: MySQL, SMTP, WebSocket 등)
    upstream tcp_backend {
        server 127.0.0.1:9000;
        server 127.0.0.1:9001;
    }

    server {
        listen     9000;          # 클라이언트가 접속하는 포트
        proxy_pass tcp_backend;   # 내부 업스트림으로 전달
        proxy_timeout 10s;
        proxy_connect_timeout 1s;
    }

    # UDP 예시 (DNS, Syslog 등)
    upstream udp_backend {
        server 127.0.0.1:1053;
        server 127.0.0.1:1054;
    }

    server {
        listen      1053 udp;
        proxy_pass  udp_backend;
    }
}
```
