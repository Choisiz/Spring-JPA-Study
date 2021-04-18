package Hello.Hellospring.service;

import Hello.Hellospring.domain.Member;
import Hello.Hellospring.repository.MemberRepository;
import Hello.Hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
public class MemberService {

    private  final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    } //new하면 테스트new랑 객체가 달라서 이렇게 바꿈(DI)

    /*
    * 회원가입
    * */
    public Long join(Member member){
        /*
        Optional<Member> result =memoryMemberRepository.findByName(member.getName());
        result.ifPresent(m -> { //값이 있으면
                throw new IllegalStateException("이미존재하는회원입니다");
        });
        Optional로 인해 가능, 기존에는 if문사용
        */
        validateDuplicateMember(member); //중복회원검증
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        //같은이름 있는 중복회원x
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //값이 있으면
                    throw new IllegalStateException("이미존재하는회원입니다");
                });
    }
    
    //전체회원조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
