package hello.spring;

import hello.spring.aop.TimeTraceAop;
import hello.spring.controller.MemberController;
import hello.spring.repository.*;
import hello.spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em, MemberRepository memberRepository) {
        this.dataSource = dataSource;
        this.em = em;
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }


/*    @Bean
    public MemberRepository memberRepository() {
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaMemberRepository(em);
    }*/
}
