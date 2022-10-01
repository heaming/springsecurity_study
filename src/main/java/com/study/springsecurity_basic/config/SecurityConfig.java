package com.study.springsecurity_basic.config;

import com.study.springsecurity_basic.auth.PrincipalDetailsService;
import com.study.springsecurity_basic.oauth.PrincipalOauth2UserService;
import com.study.springsecurity_basic.repository.JpaPersistentTokenRepository;
import com.study.springsecurity_basic.repository.PersistentLoginsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity // Spring Security filter -> Spring filter chain에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured 활성화, @PreAuthorize & @PostAuthorize 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailsService PrincipalDetailsService;

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private JpaPersistentTokenRepository tokenRepository;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//  login : code받기(인증) -> 액세스 토큰(권한) -> 사용자 정보 받기 -> 로그인 처리
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/user/**").authenticated() // 인증만 되면
                .antMatchers("/manager/**").hasAnyRole(new String[]{"ADMIN","MANAGER"})
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            .and()
                .rememberMe()
                .rememberMeParameter("remember_me")
                .tokenValiditySeconds(60*60*24*28)
                .alwaysRemember(false)
                .userDetailsService(PrincipalDetailsService)
                .tokenRepository(tokenRepository)
            .and()
                .formLogin()
                .loginPage("/loginForm")
//                .usernameParameter("userId") // html page name -> userId
                .loginProcessingUrl("/login") // login주소 호출 -> security가 대신 로그인 진행
                .defaultSuccessUrl("/")
                .failureForwardUrl("/login")
            .and()
                .logout()
//                .deleteCookies("JSESSIONID")
            .and()
                .oauth2Login()
                .loginPage("/loginForm") // google oauth : 코드가 아니라, access token+user info 받음
                .userInfoEndpoint()
                .userService(principalOauth2UserService);


    }
}
