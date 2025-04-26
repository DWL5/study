# Spring IOC

- 제어의 역전
    - 객체의 생성과 관리(생명주기 포함)을 개발자가 직접 하지 않고 스프링 컨테이너가 대신 제어하는 설계 원칙.
    - 스프링에서는 주로 IoC를 통해 DI를 실현

- IoC의 핵심 주체 : 스프링 컨테이너
    - @Component, @Service, @Controller, @Repository, @Configuration 등으로 등록된 클래스들을 Bean으로 관리하고 의존성을 주입해주는 객체

| 종류 | 설명 |
| --- | --- |
| BeanFactory | IoC 컨테이너의 가장 기본적인 형태 (지연로딩 지원) |
| ApplicationContext | BeanFactory + 국제화, 이벤트, AOP 등 스프링 기능 추가된 고급 컨테이너 |
