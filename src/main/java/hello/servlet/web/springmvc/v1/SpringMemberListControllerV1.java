package hello.servlet.web.springmvc.v1;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class SpringMemberListControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process() {
        List<Member> members = memberRepository.findAll();
        //view path setting
        ModelAndView mv = new ModelAndView("members");
        //이름이 같아서 헷갈릴 수 있지만 위에 있는 로직은 view path setting
        //아래 있는 로직은 찾은 member List를 넣어주는 로직
        mv.addObject("members", members);

        return mv;

    }
}
