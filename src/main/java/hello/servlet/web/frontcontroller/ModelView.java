package hello.servlet.web.frontcontroller;


import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    //생성자는 viewName으로만 지정해놓은 이유는 model 이 없는 경우가 있기 때문이다 => 회원가입 목록 이동 하기 같은 경우는
    //jsp path만 넣어주면 되기 때문
    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
