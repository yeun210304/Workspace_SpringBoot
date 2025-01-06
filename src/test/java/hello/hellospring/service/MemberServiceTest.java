package hello.hellospring.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;

public class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void BeforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void AfterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("pring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when : 일부러 중복가입을 발생시킨다.
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        
/*
        try {
            memberService.join(member2);    
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.12341234");
        }
*/
        //then
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("Spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        memberService.join(member2);

        List<Member> lst = new ArrayList<>(Arrays.asList(member1));
        lst.addAll(Arrays.asList(member2));

        //when
        List<Member> result = memberService.findMembers();
        
        //then
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(lst.get(i));
        }
    }

    @Test
    void findOne() {
        // given
        Member member1 = new Member();
        member1.setName("Spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        memberService.join(member2);

        //when then
        memberService.findOne(member2.getId())
            .ifPresent(m -> {
                assertThat(m).isEqualTo(member2);
            });
    }

}
