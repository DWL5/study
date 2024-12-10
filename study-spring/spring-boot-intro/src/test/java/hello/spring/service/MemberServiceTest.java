package hello.spring.service;

import hello.spring.domain.Member;
import hello.spring.repository.MemberRepository;
import hello.spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemberRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long savedId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(savedId).get();
        Assertions.assertThat(findMember.getName())
                .isEqualTo(findMember.getName());
    }

    @Test
    void join_duplicated() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);


        //then
        Assertions.assertThatThrownBy(() -> memberService.join(member2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}