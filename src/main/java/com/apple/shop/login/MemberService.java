package com.apple.shop.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRespository memberRespository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 서비스
    public String saveJoin(String username, String password, String display) {
        // 패스워드를 Bcrypt로 해싱해서 저장한다.
        Member member = new Member();
        if (username.length() <= 5) {
            log.info("---------- 유저 이름이 너무 짧습니다. --------");
            return "join.html";
        } else if (password.length() <= 8) {
            log.info("---------- 패스워드가 너무 짧습니다. --------");
            return "join.html";
        } else {
            try {
                // 모든 조건이 충족 시 DB에 저장
                log.info("회원 가입이 성공적으로 완료 되었습니다.");
                var hashPassword = passwordEncoder.encode(password);
                member.setUsername(username);
                member.setPassword(hashPassword);
                member.setDisplayName(display);
                memberRespository.save(member);
                return "redirect:/list";
            }catch (DataIntegrityViolationException e){
                // DB에 username이 존재 시 가입 실패
                log.error("해당 사용자는 이미 존재합니다.", e);
                return "join.html";
            }
        }

    }

    public String registerGet(Authentication auth) {
        // null 체크를 하지 않으면 else문에서 오류가  발생하기 때문에 반드시 해준다.
        if(auth !=null && auth.isAuthenticated()){
            return "redirect:/list";
        }else
            // 전달할 데이터 이름과, 데이터를 기입한다.
            return "join.html";
    }
}
