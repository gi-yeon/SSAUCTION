package com.ssafy.ssauction.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserPhoneNo(String userPhoneNo);
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByUserNo(Long userNo);
    Optional<Users> findByUserEmailAndUserPwd(String loginEmail, String loginPwd);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserNickname(String userNickname);

}
