package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    //회원 가입 form
    @RequestMapping("/new-form")
    public ModelAndView newForm() {
        return new ModelAndView("new-form");
    }

    //회원 list
    @RequestMapping("")
    public ModelAndView members() {
        List<Member> members = memberRepository.findAll();
        //view path setting
        ModelAndView mv = new ModelAndView("members");
        //이름이 같아서 헷갈릴 수 있지만 위에 있는 로직은 view path setting
        //아래 있는 로직은 찾은 member List를 넣어주는 로직
        mv.addObject("members", members);

        return mv;

    }

    //회원가입
    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        //front controller 에서 받은 reqeust param 값을  map 파라미터로 받아서 가져온다
        //이후 꺼내서 비지니스 로직 처리 ( 원래 서비스 및 레포지토리에서 해야하지만 여기선 하지 않음)
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        //메모리에 저장 공간 생성
        Member member = new Member(username, age);
        //메모리에 저장된 인스턴스에 회원 등록
        memberRepository.save(member);
        //view path 설정
        ModelAndView mv = new ModelAndView("save-result");
        //처리한 로직에 대한 객체를 반환해준다 return 값은 ModelView
        //mv.getModel().put("member", member);
        mv.addObject("member", member);
        return mv;
    }
}
