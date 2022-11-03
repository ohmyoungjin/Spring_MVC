package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns =  "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    //기존은 controller Map 이라는 Map 파라미터터를 V , V4 , V2 이렇게 고정으로 썼다
    //이후 handler mapping을 통해서 필요한 인스턴스를 매칭해주기 위해서
    //handlerMappingMap은 파라미터를 Object로 받는다.
    //private Map<String, ControllerV4> controllerMap = new HashMap<>();
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        //생성자 어떤 controller를 받았는지 판단해서 연결해주는 부분
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    //생성자로 mapping 정보 기입
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가가
       handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    //V3 adapter에 대한 인스턴스를 저장해둠
    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.핸들러 조회
        //MemberFormControllerV3 반환
        Object handler = getHandler(request);


        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //2.핸들러를 처리할 수 있는 핸들러 어댑터 조회
        //3.핸들러 어댑터 생성
        //handler => mapping 된 V3 인스턴스
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //4.handler 호출해서 로직실행
        //5.ModelView 반환
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        //6.viewResolver호출
        //7.MyView 반환
        MyView view = viewResolver(viewName);

        //8.render(model)호출
        view.render(mv.getModel(),request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        //url (key 값으로 해당하는 인스턴스의 타입을 찾는다)
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //handler : MemberFormControllerV3
        //현재 V3 adapter 밖에 없다.
        //안에 있는 MemberFormControllerV3는 ControllerV3의 자식이기 때문에
        //부모 타입과 동일하게 되어 adapter를 반환한다.
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
