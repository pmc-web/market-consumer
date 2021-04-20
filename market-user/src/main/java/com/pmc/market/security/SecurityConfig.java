package com.pmc.market.security;

import com.pmc.market.entity.Role;
import com.pmc.market.entity.Status;
import com.pmc.market.security.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
//                .antMatchers("/").hasRole(Role.BUYER.name())
//                .antMatchers("/").hasRole(Status.ACTIVE.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                // Oauth2 로그인 기능에 대한 여러 설정의 진입점
                .oauth2Login()
                .userInfoEndpoint()
                // 소셜 로그인 성공 후속 조치를 진행할 UserService 구현체 등록
                .userService(customOAuth2UserService);
    }
}
