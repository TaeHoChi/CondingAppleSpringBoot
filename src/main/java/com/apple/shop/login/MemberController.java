package com.apple.shop.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    String list(Authentication auth){
        return memberService.registerGet(auth);
    }

    @PostMapping("/register")
    String join(String username, String password, String display){
       return memberService.saveJoin(username, password, display);
    }

    @GetMapping("/login")
    String login(){
        // 전달할 데이터 이름과, 데이터를 기입한다.
        return "login.html";
    }

    @GetMapping("/mypage")
    // 로그인 정보를 확인한다.
    String myPage(Authentication auth) {
        // 타입 캐스팅으로 타입을 변경한다.
        CustomUser result = (CustomUser)auth.getPrincipal();
        // 전달할 데이터 이름과, 데이터를 기입한다.
        System.out.println("auth = " + result.getDisplayName());
        return "mypage.html";
    }

}

