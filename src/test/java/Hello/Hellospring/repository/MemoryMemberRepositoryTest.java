package Hello.Hellospring.repository;

import Hello.Hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();
    //MemberRepository repository = new MemoryMemberRepository();

    //메소드 실행시 마다 초기화
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("홍길동");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //아래와 같다. System.out.printf("resut:"+(result ==member));
        assertThat(member).isEqualTo(result);
    }

    @Test
    public  void findByName(){
        Member member1 = new Member();
        member1.setName("이건만");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("박경호");
        repository.save(member2);

        Member result = repository.findByName("이건만").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("이건만");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("박경호");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
