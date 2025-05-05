# 복제 (Replication)

## 개념
- 복제는 **하나의 Primary(또는 Master) DB에 쓰기 작업을 집중시키고**,  
  그 내용을 **여러 Replica(또는 Slave) DB로 복사해 읽기 부하를 분산**시키는 구조입니다.
- 주로 **읽기 트래픽 분산, 고가용성 확보, 장애 대비** 등의 목적으로 사용됩니다.

---

## 구조 예시

```
쓰기 요청  →  Primary DB (쓰기 전용)
읽기 요청  →  Replica DB 1, Replica DB 2 ... (읽기 전용)
```

- Primary: 모든 쓰기 작업은 여기서 처리
- Replica: 복제된 데이터를 기반으로 읽기 처리만 수행

---

## 복제 방식 비교

| 항목                | 동기 복제 (Synchronous)        | 비동기 복제 (Asynchronous)         |
|---------------------|----------------------------------|-------------------------------------|
| 응답 타이밍         | 모든 Replica 반영 후 응답        | Primary에 쓰기 완료 시 바로 응답   |
| 데이터 일관성       | 강한 일관성 (Strong Consistency) | 약한 일관성 (Eventually Consistent) |
| 쓰기 성능           | 느림                            | 빠름                                |
| 장애 발생 시        | 안전하지만 느릴 수 있음          | 빠르지만 데이터 유실 가능           |
| 사용 사례           | 금융, 거래 시스템 등              | 일반 웹 서비스 (MySQL 기본 설정)   |

---

## 실무 운영 전략

- 대부분의 서비스는 **비동기 복제**를 사용함 (기본 성능이 뛰어나고 장애 허용 가능)
- 쓰기 직후의 읽기 요청(Read-after-Write)은 반드시 Primary에서 처리
- Spring에서는 `@Transactional(readOnly = true)` 여부에 따라 라우팅 처리

```java
@Transactional(readOnly = true)
public List<Post> getPosts() {
    // Replica에서 읽기
}

@Transactional
public void createPost(Post post) {
    // 반드시 Primary에서 처리
}
```

- 일부 시스템에서는 **Semi-Sync**나 **복제 지연 모니터링 시스템**을 통해 안정성 보완

---

## 장점

- **읽기 성능 향상**: 많은 사용자가 조회해도 Replica가 분산 처리
- **고가용성**: Primary 장애 시 Replica를 승격해 서비스 지속 가능
- **백업/보고 분리**: 분석 쿼리나 리포트는 Replica에서 돌려서 운영 성능 저하 방지

---

## 단점 및 고려사항

- 비동기 복제 시 **최신 데이터가 바로 반영되지 않을 수 있음**
- Primary → Replica 간의 **복제 지연(Lag)** 모니터링 필요
- Replica는 일반적으로 쓰기를 허용하지 않으며, 읽기만 수행함

---

## 요약

- 복제는 읽기 트래픽을 분산시키고 고가용성을 제공하는 전략
- Primary는 쓰기 전용, Replica는 읽기 전용으로 구분
- 대부분의 서비스는 비동기 복제를 사용하고, 읽기/쓰기 분리를 통해 성능을 확보함

---

## 참고 강의  
[스프링부트로 대규모 시스템 설계 - 게시판 프로젝트](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8%EB%A1%9C-%EB%8C%80%EA%B7%9C%EB%AA%A8-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%84%A4%EA%B3%84-%EA%B2%8C%EC%8B%9C%ED%8C%90/dashboard)

---

## 읽기/쓰기 분리를 위한 라우팅 구현 (Spring Boot)

### 개요
- 복제 환경에서는 `Primary`에 쓰기 요청을, `Replica`에 읽기 요청을 보내는 것이 성능상 유리합니다.
- Spring Boot에서는 `@Transactional(readOnly = true)` 여부를 기준으로 DataSource를 자동 분기할 수 있습니다.
- 이 섹션에서는 `RoutingDataSource`, `ThreadLocal`, `AOP`를 활용해 자동 읽기/쓰기 분기 처리를 구현하는 예시를 설명합니다.

---

### 1. application.yml 설정

```yaml
spring:
  datasource:
    primary:
      url: jdbc:mysql://primary-db:3306/myapp
      username: root
      password: pass
    replica:
      url: jdbc:mysql://replica-db:3306/myapp
      username: root
      password: pass
```

---

### 2. DataSourceConfig 클래스

```java
@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource routingDataSource(
        @Qualifier("primaryDataSource") DataSource primary,
        @Qualifier("replicaDataSource") DataSource replica
    ) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("PRIMARY", primary);
        targetDataSources.put("REPLICA", replica);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(primary);

        return routingDataSource;
    }

    @Bean
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://primary-db:3306/myapp")
            .username("root")
            .password("pass")
            .build();
    }

    @Bean
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://replica-db:3306/myapp")
            .username("root")
            .password("pass")
            .build();
    }
}
```

---

### 3. RoutingDataSource 구현

```java
public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.get(); // "PRIMARY" 또는 "REPLICA"
    }
}
```

---

### 4. ThreadLocal 기반 컨텍스트

```java
public class DbContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setPrimary() {
        CONTEXT.set("PRIMARY");
    }

    public static void setReplica() {
        CONTEXT.set("REPLICA");
    }

    public static String get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
```

---

### 5. AOP로 자동 분기 처리

```java
@Aspect
@Component
public class ReadOnlyRoutingAspect {

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}

    @Before("transactionalMethods() && @annotation(tx)")
    public void beforeTransactional(Transactional tx) {
        if (tx.readOnly()) {
            DbContextHolder.setReplica();
        } else {
            DbContextHolder.setPrimary();
        }
    }

    @After("transactionalMethods()")
    public void clear() {
        DbContextHolder.clear();
    }
}
```

---

### 6. 사용 예시

```java
@Service
public class PostService {

    @Transactional(readOnly = true)
    public List<Post> getPosts() {
        // 읽기 전용 → Replica로 라우팅
    }

    @Transactional
    public void createPost(Post post) {
        // 쓰기 → Primary로 라우팅
    }
}
```

---

### 요약

| 항목 | 설명 |
|------|------|
| Primary | 모든 쓰기 요청을 담당 |
| Replica | 읽기 요청을 분산 처리 |
| @Transactional(readOnly = true) | Replica로 자동 라우팅됨 |
| AOP + ThreadLocal | 트랜잭션 성격에 따라 적절한 DataSource 선택 |

---

### 참고

- 복제 지연이 민감한 서비스라면 Read-after-Write 시에는 반드시 Primary에서 읽도록 예외처리 필요
- Replica의 수가 많을 경우 Load Balancer나 Round-Robin 전략 적용 가능
