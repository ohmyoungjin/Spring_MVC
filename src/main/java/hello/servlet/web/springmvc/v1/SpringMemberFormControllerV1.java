package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@Controller
//스프링이 자동으로 스프링 빈으로 등록한다.
//내부에 @Component annotation이 있어서 컴포넌트 스캔의 대상이 된다.
@Controller
//이렇게 두 개 적는 것도 적용이 가능하다.
//RequestMapping이 class level에 있으면 mapping할 클래스로 인식한다.
//@Component
//@RequestMapping
public class SpringMemberFormControllerV1 {

    //@RequestMapping
    //요청 정보를 맵핑한다.
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
