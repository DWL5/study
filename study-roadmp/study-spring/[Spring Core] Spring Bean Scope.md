# Spring Bean Scope 정리

스프링 프레임워크에서 `빈(Bean)`은
스프링 IoC 컨테이너에 의해 인스턴스화되고, 조립되며, 관리되는 객체를 의미합니다.
스프링 컨테이너의 핵심 기능 중 하나는
빈의 생명주기를 관리하는 능력입니다.
여기에는 빈을 생성하고, 설정하고, 필요에 따라 소멸시키는 과정이 포함됩니다.
스프링 컨테이너가 빈의 생명주기를 제어하는 방법 중 하나가
빈의 스코프(Scope)를 지정하는 것입니다.

# 1. Bean Scope란?

> 빈(Bean)이 스프링 컨테이너 안에서 어떤 생명주기(Lifecycle)를 가지는지 지정하는 것

- 스프링은 기본적으로 객체를 싱글톤으로 관리하지만,
- 필요에 따라 요청마다 새로 만들거나, 세션마다 다르게 만들 수도 있다.

---

# 2. 주요 스코프 종류

| 스코프 | 설명 | 사용 상황 |
| --- | --- | --- |
| singleton | 스프링 컨테이너당 하나의 인스턴스 | 기본값, 대부분의 서비스/리포지토리 빈 |
| prototype | 요청할 때마다 새 인스턴스 생성 | 매번 새로운 객체가 필요할 때 |
| request | HTTP 요청당 하나의 인스턴스 (웹) | 웹 요청별 상태 관리 |
| session | HTTP 세션당 하나의 인스턴스 (웹) | 로그인 세션 정보 저장 |
| application | ServletContext당 하나의 인스턴스 | 애플리케이션 전체 범위 공유 데이터 |
| websocket | WebSocket 세션당 하나의 인스턴스 | WebSocket 연결별 상태 관리 |

---

# 3. 각 스코프 자세히 살펴보기

## singleton (기본 스코프)

- 컨테이너에 1개만 존재
- 애플리케이션이 살아있는 동안 유지
- 대부분의 스프링 빈은 싱글톤으로 사용

```java
@Component
@Scope("singleton") // 생략해도 기본값
public class SingletonBean {
}
```

## prototype

- 요청할 때마다 새로운 객체 생성
- 스프링 컨테이너는 빈을 주입한 이후 관리하지 않음 (초기화까지만 관여)

```java
@Component
@Scope("prototype")
public class PrototypeBean {
}
```

주의: @PreDestroy 같은 소멸 콜백이 호출되지 않는다.

## request

- HTTP 요청 하나당 새로운 빈 생성
- 요청이 끝나면 빈도 소멸

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestBean {
}
```

## session

- HTTP 세션 하나당 새로운 빈 생성
- 세션이 종료될 때 빈도 소멸

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {
}
```

## application

- ServletContext(ApplicationContext)당 하나의 인스턴스
- 서버 전체에서 공유할 데이터에 적합

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class ApplicationScopeBean {
}
```

## websocket

- WebSocket 세션 하나당 새로운 빈 생성

```java
@Component
@Scope(value = "websocket")
public class WebSocketScopeBean {
}
```

---

# 4. 정리 요약

| 스코프 | 라이프사이클 | 사용 주기 |
| --- | --- | --- |
| singleton | 애플리케이션 전체 | 거의 모든 서비스/리포지토리 |
| prototype | 요청 시마다 새로 생성 | 새로운 객체가 매번 필요할 때 |
| request | HTTP 요청마다 새로 생성 | 웹 요청별 데이터 유지 |
| session | HTTP 세션마다 새로 생성 | 로그인 상태 유지 등 |
| application | ServletContext 전체에 하나 | 글로벌 설정/데이터 공유 |
| websocket | WebSocket 세션마다 새로 생성 | WebSocket 연결별 상태 관리 |

---

# 5. 스코프 별 관리 책임

| 스코프 | 생성 | 소멸 관리 |
| --- | --- | --- |
| singleton | 스프링이 관리 | 스프링이 관리 |
| prototype | 스프링이 생성만 | 소멸은 개발자가 직접 관리 |
| request/session/application/websocket | 스프링이 관리 | 스프링이 관리 |

---

# 핵심 문장

> 싱글톤이 기본이지만, 특별한 경우에는 필요한 라이프사이클에 맞춰 스코프를 설정해줘야 한다.
