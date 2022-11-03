package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        System.out.println("v3 맨 처음 실행");
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.out.println("V3requestURI = " + requestURI);

        //어떤 controller을 사용할 건지 mapping 하는 부분
        //1.컨트롤러 조회
        ControllerV3 controller = controllerMap.get(requestURI);
        //해당 하는 인스턴스가 없으면 404 page로 보내는 부분
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap make
        Map<String, String> paramMap = createParamMap(request);
        //2.컨트롤러 호출
        //3.ModelView 반환
        //mv = view path , view 에서 보여져야 할 정보를 담고 있다
        //ViewName : view path
        //model : view 에서 보여져야 할 정보
        ModelView mv = controller.process(paramMap);
        //논리 이름 ex) new-form 물리 경로를 설정해주는 view resolver가 필요하다.
        String viewName = mv.getViewName();
        //여기서 물리 경로를 설정해준다.
        //인스턴스를 생성하면서 생성자를 통해 view path를 설정한다.
        //4.viewResolver 호출
        //5.MyView 반환
        MyView view = viewResolver(viewName);
        //회원 목록
        //6.render 호출
        System.out.println("임의로 만든 model mv.getModel : " + mv.getModel()); // 회원가입 목록으로 갈 때는 {} 값이 나온다
        System.out.println(mv.getModel().get("username"));
        System.out.println(mv.getModel().get("age"));
        view.render(mv.getModel(),request, response);
    }

    //view 이름을 물리 이름으로 반환해주는 Method MyView를 return 한다.
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //request param controller에 넘기기 위한 Map을 반환하는 Method
    //이 안에는 요청한 param 값들이 있다
    //ex) key : username, value : request.getParameter("username");
    //    key : age , value : request.getParameter("age");
    //request.getParameterNames() reqeust 온 모든 parameter를 다 꺼낸다.
    //forEachRemaining for문으로 돌린다 변수명 = paramName (key 값) request.getParameter(paramName) (value 값)
    //createParamMap paramName(key) : usernamevalue : maeng2
    //createParamMap paramName(key) : agevalue : 28 과 같은 값을 map에 put 한다.
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println("createParamMap paramName(key) : " + paramName + "value : " + request.getParameter(paramName) ) );
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
