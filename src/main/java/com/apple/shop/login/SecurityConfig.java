package com.apple.shop.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //PasswordEncoder를 해야 Spring Security가 잘 동작한다.
    // new로 매번 값을 뽑는 것이 아닌 미리 뽑아서 적용한다.
    // 아무곳이나 사용할 수 있다.
    // PasswordEncoder가 유저가 제출한 비번 & DB 비번을 비교한다.
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //추가
    // CSRF 기능을 킨다.
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }


    // filerChain : 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행 해주고 싶은 코드를 담는다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // (함수 파라미터) -> {함수 내용} == class(){} -> new ~
        // csrf를 끌수 있다.
         http.csrf((csrf) -> csrf.disable());

//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
//                // CSRF를 /login 페이지는 끈다.
//                .ignoringRequestMatchers("/login")
//        );

        // 특정 페이지 로그인 검사 할지 결정
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() //permitAll 항상 허용
        );
        // 폼으로 로그인한다.
        http.formLogin((formLogin) ->
                // 폼 로그인 위치
                formLogin.loginPage("/login").
                        // 로그인 성공시 이동할 URL
                        defaultSuccessUrl("/mypage")
        );
        // 로그 아웃
        // logout으로 GET 요청하면 가능하다.
        http.logout(logout -> logout.logoutUrl("/logout"));

        return http.build();
    }
}