package com.muzik.logincustom.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> {
           auth.requestMatchers("/", "/index").permitAll();                   // 메인페이지(모든사용자 권한)
           auth.requestMatchers("/register").permitAll();                       // 회원등록페이지(모든사용자 권한)
           auth.requestMatchers("/login").permitAll();                          // 로그인페이지(모든사용자 권한)
           auth.requestMatchers("/logout").permitAll();                         // 로그인페이지(모든사용자 권한)
           auth.requestMatchers("/user").hasAnyRole("USER", "ADMIN");     // 로그인페이지(모든사용자 권한)
           auth.requestMatchers("/admin").hasAnyRole("ADMIN");              // 로그인페이지(모든사용자 권한)
        });

        http.formLogin(login -> login
                .defaultSuccessUrl("/", true)           // 로그인 성공시 메인페이지 이동
                .loginPage("/login")                                           // 로그인폼은 사용자가 작성한 /login으로 이동
                .usernameParameter("userid")                                   // userid를 username에 적용
                .permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);

        http.logout(logout -> logout
                .logoutUrl("/logout")                                         // 로그아웃 맵핑명
                .logoutSuccessUrl("/login")                                   // 로그아웃 성공시 로그인페이지로 이동
        );

        return http.build();
    }

}