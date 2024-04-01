package com.apple.shop.login;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRespository extends JpaRepository<Member, Long> {

    // findBy컬럼명(원하는 데이터)
    // Optional은 못찾을 것을 대비해 사용한다.
    // All을 붙이면 다 찾아온다.
    // Derived query methods
    Optional<Member> findByUsername(String username);

}
