package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

///front-controller/v1/ 해당하는 하위 file들이 호출 될 때 마다 이 Controller을 호출한다.
//이 class file이 Front controller가 된다.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    //Map의 첫 번째 인자는 url 정보 , 두 번째 인자는 해당하는 controller을 갖게 된다.
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    //생성자로 만들어서 해당하는 url로 들어오면 맞는 controller key 값과 함께 인스턴스를 Map에 저장해둔다.
    public FrontControllerServletV1() {
        System.out.println("맨 처음 실행");
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");
        //해당하는 uri 정보를 가져올 수 있다.
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        //처음 호출되면서 저장해두었던 key 값으로 해당하는 인스턴스를 찾아서
        //controller 객체를 만든다
        //ex) front-controller/v1/members/new-form => new MemberFormControllerV1()
        //ControllerV1 controller = new MemberListControllerV1(); 이거와 동일하다고 보면 된다.
        ControllerV1 controller = controllerMap.get(requestURI);
        System.out.println("Front Controller mapping controller : " + controller);
        //controller객체가 없을 경우 404 Status를 반환해준다.
        if (controller == null) {
            System.out.println("404 넘어온다");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //Front controller 에서 호출한 Controller Mapping하여 호출 하여
        //override 된 로직이 호출된다.
        //이후 비즈니스 로직 처리 및 model 담기 view forward는 해당 하는 controller에서 처리하도록 넘겨준다.
        controller.process(request, response);

    }
}
