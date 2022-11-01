package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username  = " + username);

        //내가 보낼 contentType을 설정
        response.setContentType("text/plain");
        //언어 설정 2020년 이후이기 때문에 utf-8을 사용하는게 맞다
        response.setCharacterEncoding("utf-8");
        //request로 요청 받은 값을 response로 돌려준다.
        response.getWriter().write("hello " + username);
    }


}
