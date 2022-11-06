package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    //회원 가입 form
    //@GetMapping ("/new-form")
    @RequestMapping(value = "/new-form", method = RequestMethod.GET)
    public String newForm() {
        System.out.println("들어옵니까?");
        return "new-form";
    }

    //회원가입
    @PostMapping("/save")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("age") int age,
            Model model) {
        //front controller 에서 받은 reqeust param 값을  map 파라미터로 받아서 가져온다
        //이후 꺼내서 비지니스 로직 처리 ( 원래 서비스 및 레포지토리에서 해야하지만 여기선 하지 않음)
        //메모리에 저장 공간 생성
        Member member = new Member(username, age);
        //메모리에 저장된 인스턴스에 회원 등록
        memberRepository.save(member);

        model.addAttribute("member", member);

        return "save-result";
    }

    //회원 list
    @GetMapping("")
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();
        //view path setting
        model.addAttribute("members", members);
        return "members";

    }
}
