package com.apple.shop.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// implements는 MyUserDetailsService가 UserDetailsService를 따르는 가 검사한다.
// 함수를 강제로 하고 싶으면 사용한다.
// 특정 규격에 맞게 코드를 쓰는 것이다.
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    // 의존성 주입
    private final MemberRespository memberRespository;

    // UserDetailsSErvice가 DB에서 동일 아이디의 유저 정보를 꺼내온다.
    // 세션과 로그인 시간 등을 저장해준다.
    // Spring Security 가 유저가 제출한 비번 == DB에 있는 비번을 알려주는 코드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 username을 가진 유저를 찾아와서
        // User(유저아이디, 비번, 권한)
        var result = memberRespository.findByUsername(username);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("아이디 없음");
        }
        // Optional 타입이라 사용한다.
        var user = result.get();

        // 권한 부여
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 나중에 API에서 권한을 알수 잇게 메모한다.
        authorities.add(new SimpleGrantedAuthority("일반유저"));

        var a = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        a.setDisplayName(user.getDisplayName());
        return a;
    }

}

// 원하는 혀앹로 User를 만든다.User(user.getUsername(), user.getPassword(), authorities)
@Setter
@Getter
class CustomUser extends User{

    private String displayName;

    public CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities
    ) {
        // extends한 값을 그대로 가지고 온다.
        super(username, password, authorities);
    }


}
