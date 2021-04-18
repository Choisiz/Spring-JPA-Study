package Hello.Hellospring;

import Hello.Hellospring.repository.*;
import Hello.Hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

   private DataSource dataSource;
   private EntityManager em;
   private  final MemberRepository memberRepository;
    /*@Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public  SpringConfig(EntityManager em){
        this.em=em;
    }
     */
    @Autowired
    public  SpringConfig(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    /*@Bean
    public MemberRepository memberRepository(){
        // return  new MemoryMemberRepository(); //메모리접근
        //return new JdbcMemberRepository(dataSource); //순수jdbc
        // return new JdbcTemplateMemberRepository(dataSource); //jdbc Template
        //return new JpaMemberRepository(em);
    }*/
}
