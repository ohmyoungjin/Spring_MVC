package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        //view path setting
        ModelView mv = new ModelView("members");
        //이름이 같아서 헷갈릴 수 있지만 위에 있는 로직은 view path setting
        //아래 있는 로직은 찾은 member List를 넣어주는 로직
        mv.getModel().put("members", members);

        return mv;

    }
}
