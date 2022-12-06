package com.ssafy.ssauction.service.auth;

import com.ssafy.ssauction.domain.users.Users;
import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private Users users;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        return usersRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
