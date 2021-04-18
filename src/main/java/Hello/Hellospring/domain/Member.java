package Hello.Hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {
    //@Id: primary key 역할
    //@GeneratedValue: primary key 값을 위한 자동생성전략
    //자동생성전략4가지(IDENTITY,SEQUENCE,TABLE,AUTO)
    //GenerationType.IDENTITY: 각 엔티티클래스 마다 독립적으로 id가 자동증가 되어짐
    //IDENTITY: 기본키 생성을 데이터 베이스에 위임
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;

    public Long getId() {
        return id;
    }

    //만약 name이 아니라 useName이면 @Column(name="userName")설정하면됨
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
