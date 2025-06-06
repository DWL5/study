# 도커 컨테이너(Docker Container)

컨테이너는 가볍고, 이식 가능하며, 고립된 소프트웨어 실행 환경으로,
개발자가 애플리케이션과 그에 필요한 모든 의존성을 함께 패키징하고 다양한 플랫폼에서 일관되게 실행할 수 있도록 해줍니다.
이러한 컨테이너는 애플리케이션 개발, 배포, 운영 과정을 간소화해주며,
기반 인프라가 무엇이든 상관없이 애플리케이션이 항상 동일하게 동작하도록 보장합니다.

> 도커 컨테이너는 **애플리케이션을 실행하기 위한 가볍고, 이식 가능하며, 독립적인 실행 환경**입니다.

---

## 도커 컨테이너란?

도커 컨테이너는 호스트 OS 위에서 애플리케이션과 그 실행에 필요한 모든 것(코드, 라이브러리, 설정 파일 등)을 함께 패키징해서 **고립된 환경**에서 실행할 수 있게 해줍니다.

---

## 🔍 주요 특징

| 항목         | 설명 |
|--------------|------|
| **경량화**    | 전체 OS를 포함하는 가상 머신과 달리, 커널을 공유하므로 훨씬 가볍고 빠름 |
| **이식성**    | 어느 환경이든 동일한 이미지로 동일하게 실행 가능 (로컬, 서버, 클라우드 등) |
| **격리성**    | 서로 다른 컨테이너는 독립된 공간에서 실행되며, 충돌 없이 공존 가능 |
| **빠른 시작** | 부팅 없이 즉시 실행 가능 |
| **인프라 독립** | 실행 환경이 같지 않아도 동일한 컨테이너 이미지로 동작 보장 |

---

## 컨테이너 vs 이미지

- **이미지 (Image)**: 실행 가능한 애플리케이션을 담은 "설계도"
- **컨테이너 (Container)**: 이미지로부터 **실행 중인 인스턴스**

> 이미지는 변하지 않지만, 컨테이너는 실행되면서 상태가 생깁니다.

---

## 컨테이너 동작 구조

1. `Dockerfile` 작성 (설정/명령어 등 정의)
2. `docker build` → 이미지 생성
3. `docker run` → 이미지로부터 컨테이너 실행
4. 컨테이너 내부에서 앱이 독립적으로 동작

---

## 실행 예시

```bash
# nginx 웹서버 컨테이너 실행 (포트 80 노출)
docker run -d -p 80:80 --name webserver nginx
