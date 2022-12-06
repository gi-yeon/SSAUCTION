package com.ssafy.ssauction.config;

import com.ssafy.ssauction.auth.AuthenticationEntryPointHandler;
import com.ssafy.ssauction.auth.CustomOAuth2UserService;
import com.ssafy.ssauction.auth.JwtAuthenticationFilter;
import com.ssafy.ssauction.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationEntryPointHandler authenticationEntryPointHandler;

    private final AccessDeniedHandler accessDeniedHandler;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 세션 사용하지 않음
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // 해당 경로는 모두 허용
                .antMatchers("/swagger*/**", "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**", "/swagger.json", "/users/login",
                        "/users/logout", "/users/join", "/users/refresh",
                        "/users/token", "/users/findId/**", "/users/resetPwd/**",
                        "/users/sendSMS/**","/houses/searchAll", "/board/**", "/comment/**",
                        "/houses/searchHot", "/users/checkEmail/**", "/users/checkNickname/**"
                )
                .permitAll()
                .antMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/houses/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/likes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/board/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/file/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/comment/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/categories/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/sessions/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()// 그 외 모두 인증된 사용자만 허용

                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler)
                .accessDeniedHandler(accessDeniedHandler)
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .and()
//                .oauth2Login()
                // .userInfoEndpoint()
                // .userService(customOAuth2UserService)
                .and()
                .build();
    }

    // 비밀번호 암호화 시 사용
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
