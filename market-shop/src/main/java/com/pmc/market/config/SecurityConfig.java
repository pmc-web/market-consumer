package com.pmc.market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/","/css/**","/images/**","/js/**", "/shops/**").permitAll()
//                .antMatchers("/").hasRole(Role.BUYER.name())
//                .antMatchers("/").hasRole(Status.ACTIVE.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/") ;
    }
}
