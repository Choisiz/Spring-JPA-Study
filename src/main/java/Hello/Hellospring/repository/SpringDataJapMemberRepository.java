package Hello.Hellospring.repository;

import Hello.Hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJapMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //JpaRepository받고 있으면 SpringDataJapMemberRepository가 자동으로 구현클래스를 만들어준다.
    //이후 스프링 Bean에 자동으로 등록해준다
    Optional<Member> findByName(String name);
}
