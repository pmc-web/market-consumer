package com.pmc.market.config;

import com.pmc.market.config.filter.JwtAuthenticationFilter;
import com.pmc.market.error.LoginFailHandler;
import com.pmc.market.security.auth.CustomAuthenticationFilter;
import com.pmc.market.security.auth.CustomAuthenticationProvider;
import com.pmc.market.security.auth.CustomLoginSuccessHandler;
import com.pmc.market.security.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/users/sign-up", "/users/login", "/auth/**").permitAll() // 가입 및 인증 주소는 누구나 접근가능
//                .antMatchers(HttpMethod.GET, "helloworld/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
                .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다

    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }
}
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
//                .anyRequest().permitAll()
////                .anyRequest().hasRole("USER")
//                .and()
//                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // form 기반의 로그인에 대해 비활성화 한다.
//                .formLogin()
//                .disable()
//                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/**", "/configuration/ui", "**/swagger-resources/**",
//                "**/swagger-ui/**", "**/configuration/security", "/swagger-ui.html/**", "**/webjars/**", "**/swagger**", "/assets/**");
//    }
//
//    @Bean
//    public AuthenticationFailureHandler failureHandler() {
//        return new LoginFailHandler();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    // jwt token filter
//    @Bean
//    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
//        customAuthenticationFilter.setFilterProcessesUrl("/users/token");
//        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
//        customAuthenticationFilter.afterPropertiesSet();
//        return customAuthenticationFilter;
//    }
//
//    @Bean
//    public CustomLoginSuccessHandler customLoginSuccessHandler() {
//        return new CustomLoginSuccessHandler();
//    }
//
//
//    @Bean
//    public CustomAuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider(passwordEncoder());
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
//        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider());
//    }
//
//}