package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {

    //v2와 달라진 점은 servlet에 종속적이지 않고 framework에 종속적이라는 점이다 !
    ModelView process(Map<String, String> paramMap);
}
