package Hello.Hellospring.service;

import Hello.Hellospring.domain.Member;
import Hello.Hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void BeforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member =new Member();
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    void 중복회원예외() {
        //given
        Member member1 = new Member();
        member1.setName("이건만");

        Member member2 = new Member();
        member2.setName("이건만");

        //when
        //방법1
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () ->
                memberService.join(member2));
        /*방법2
        memberService.join(member1);
        try{
          memberService.join(member2);
          fail();
        }catch(IllegalStateException e){
           aeeertThat(e.getMessage()).isEqualTo("이미존재하는 회원입니다.");
        }
        */
    }

    @Test
    void findOne() {
    }
}