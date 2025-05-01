# gRPC란?

## 개요

gRPC는 Google에서 개발한 오픈소스 **고성능 RPC(Remote Procedure Call)** 프레임워크이다.  
클라이언트가 원격 서버의 함수를 마치 로컬 함수처럼 호출할 수 있게 해주는 구조이다.

---

## 주요 특징

| 항목 | 설명 |
| --- | --- |
| 프로토콜 | HTTP/2 기반 |
| 메시지 포맷 | Protobuf (Protocol Buffers) - 바이너리 직렬화 |
| API 정의 | .proto 파일을 통해 서비스 인터페이스 정의 |
| 성능 | 빠르고 가볍다 (텍스트가 아닌 바이너리 사용) |
| 통신 방식 | 단방향, 양방향 스트리밍 지원 |
| 코드 생성 | 다양한 언어에서 클라이언트/서버 코드 자동 생성 지원 |

---

## 작동 방식

1. `.proto` 파일에서 서비스와 메시지 구조를 정의한다.
2. `protoc` 컴파일러를 통해 클라이언트/서버용 코드가 생성된다.
3. 클라이언트는 생성된 코드를 사용하여 원격 서버의 메서드를 호출한다.
4. 메시지는 **Protobuf** 형식으로 직렬화되어 HTTP/2 기반으로 전송된다.

---

## .proto 예시

```proto
syntax = "proto3";

service Greeter {
  rpc SayHello (HelloRequest) returns (HelloReply);
}

message HelloRequest {
  string name = 1;
}

message HelloReply {
  string message = 1;
}
```

---

## gRPC 사용 시 JAR 의존성 추가 이유

gRPC는 일반적인 HTTP 기반 REST API와 달리:

- **HTTP/2 통신**을 사용하고
- **Protobuf 바이너리 포맷**으로 메시지를 주고받으며
- **.proto 파일을 바탕으로 자동 생성된 Stub 코드**를 통해 통신한다

그래서 Java 프로젝트에서는 **다음과 같은 라이브러리를 반드시 추가**해야 한다:

```kotlin
// Gradle 예시 (build.gradle.kts)
dependencies {
    implementation("io.grpc:grpc-netty-shaded:1.60.0")        // 전송 계층
    implementation("io.grpc:grpc-protobuf:1.60.0")             // 메시지 직렬화
    implementation("io.grpc:grpc-stub:1.60.0")                 // Stub 생성을 위한 gRPC 코드
    implementation("com.google.protobuf:protobuf-java:3.24.0") // Protobuf 런타임
}
```

---

## Java 코드 생성 예시

`.proto` 파일을 기반으로 Java 코드를 생성하려면 `protoc` 플러그인을 사용한다.

```bash
protoc --java_out=./build/generated --proto_path=./src/main/proto greeter.proto
```

gRPC 코드를 추가로 생성하려면:

```bash
protoc --plugin=protoc-gen-grpc-java \
       --grpc-java_out=./build/generated \
       --proto_path=./src/main/proto \
       greeter.proto
```

---

## REST API와의 비교

| 항목 | REST API | gRPC |
| --- | --- | --- |
| 전송 포맷 | JSON (텍스트 기반) | Protobuf (바이너리 기반) |
| 명세 방식 | URI + HTTP 메서드 조합 | .proto 파일로 명확하게 정의 |
| 속도 및 크기 | 상대적으로 느림 | 빠르고 데이터 크기 작음 |
| 스트리밍 | 제한적 | 양방향 스트리밍 지원 |
| 브라우저 호환 | HTTP/1.1로도 가능 | 브라우저에서 직접 사용은 어려움 (gRPC-Web 필요) |
| 학습 난이도 | 쉬움 | 비교적 높음 (도구와 개념 필요) |

---

## 장점

- 매우 빠른 성능 (바이너리 포맷, HTTP/2)
- 양방향 스트리밍 및 비동기 통신 지원
- 다양한 언어 지원 (Java, Go, Python, C++, Node.js 등)
- 서비스 명세가 명확하고 일관성 있음

---

## 단점

- JSON보다 디버깅이 어렵다
- 브라우저 호환성이 낮아 gRPC-Web 필요
- 초기 설정과 학습 곡선이 다소 높음

---

## 한 줄 요약

> gRPC는 Protobuf와 HTTP/2를 활용해 빠르고 구조화된 API 통신을 제공하는 현대적인 RPC 프레임워크이며,  
> Java에서는 이를 위해 관련 JAR 의존성 추가와 .proto 기반 코드 생성이 필요하다.
