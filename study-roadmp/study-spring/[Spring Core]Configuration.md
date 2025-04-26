# [Spring Core] Configuration

스프링에서 Configuration이란 Bean을 정의하고 등록하는 방법을 의미합니다.

어떤 객체를 스프링 컨테이너에 등록하고, 어떻게 의존성을 주입할지 정의하는 방법이에요.

| 방법 | 대표예시 |
| --- | --- |
| XML 기반 | applicationContext.xml |
| Java 기반 | @Configuration, @Bean |
| 어노테이션 기반 자동 구성 | @ComponentScan 사용 |

## 수동 구성 @Configuration

- @Bean으로 등록된 객체들은 싱글톤으로 관리
- 스프링은 내부적으로 CGLIB 프록시를 사용하여 @Bean 메서드가 여러 번 호출되더라도 같은 인스턴스 반환

```java
@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

## 자동 구성 @ComponentScan + @Component

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AutoAppConfig {
}
```

```java
@Component
public class MemberServiceImpl implements MemberService {
    ...
}
```

| 항목 | 수동 설정 (@Bean) | @자동 설정 (@Component) |
| --- | --- | --- |
| 제어 범위 | 명확하게 지정 가능 | 간편하지만 범위가 넓음 |
| 객체 생성 시점 | 커스터마이징 쉬움 | 생성 시점 제어 어려움 |
| DI 방식 | 코드를 명시적으로 구성 | 스프링이 자동으로 처리 |

## @Configuration과 @Bean은 언제 사용하는가?

- 실무에서는 @Bean 수동 설정도 자주 사용
    - 외부 라이브러리나 우리가 직접 컨트롤하기 어려운 클래스들은 @Component과 같은 어노테이션을 붙일 수 없음

```java
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        return new RedisTemplate<>();
    }
}
```