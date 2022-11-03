package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//rendering 하는 class
public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    //v2 render
    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    //v3 render overloading
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("render start !!");
        //view에 필요한 parameter를 담아주는 method
        modelToRequestAttribute(model, request);
        //view로 이동시킨다.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        //model에 담겨진 회원 목록 저장 및 회원 목록을 임의로 생성한 modelMap 에서 request Model 으로 옮김
        //***말 그대로 model 자체를 옮긴다 안에 있는 값을 일일이 옮기는 것이 아닌 model 자체 주소 값을 옮김
        //이렇게 생성된 request model 은 view 에 그려지게 된다.
        //JSP는 request.getAttribute() 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서
        //request.setAttribute() 로 담아둔다.
        //JSP로 포워드 해서 JSP를 렌더링 한다.
        System.out.println("modelToRequestAttribute start !!");
        //model이 {} 값으로 없으면 로직자체를 실행시키지 않는다 nullPoint Exception이 안나는게 신기함
        model.forEach((key, value)-> System.out.println("model key : " + key + " model value : " + value));
        model.forEach((key, value)-> request.setAttribute(key, value));
    }
}
