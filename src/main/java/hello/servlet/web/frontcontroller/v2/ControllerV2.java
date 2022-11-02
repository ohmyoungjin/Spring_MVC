package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    //v1과 차이점 v1은 return 값이 void로 없었지만
    //v2는 MyView를 반환하여 어떤 jsp로 render 할 지 path 포함한 객체를 넘긴다.
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
