package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        //view 는 controller을 거쳐서 접근해야 한다. 그 때 접근을 도와주는 객체이다.
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        //servlet에서 jsp 호출하는 로직
        //서버끼리 내부에서 호출함
        dispatcher.forward(request, response);
    }
}
