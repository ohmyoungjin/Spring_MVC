package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        //front controller 에서 받은 reqeust param 값을  map 파라미터로 받아서 가져온다
        //이후 꺼내서 비지니스 로직 처리 ( 원래 서비스 및 레포지토리에서 해야하지만 여기선 하지 않음)
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        //메모리에 저장 공간 생성
        Member member = new Member(username, age);
        //메모리에 저장된 인스턴스에 회원 등록
        memberRepository.save(member);
        //view path 설정
        ModelView mv = new ModelView("save-result");
        //처리한 로직에 대한 객체를 반환해준다 return 값은 ModelView
        mv.getModel().put("member", member);
        return mv;
    }
}
