package Hello.Hellospring.controller;

import Hello.Hellospring.domain.Member;
import Hello.Hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/member/new")
    public String createForm(){
        return "member/createMemberForm";
    }

    @PostMapping("/member/new") //보통 데이터 입력시 post
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/member")
    public String list(Model model){
        List<Member> member = memberService.findMembers();
        model.addAttribute("member",member);
        return "member/memberList";
    }
}
