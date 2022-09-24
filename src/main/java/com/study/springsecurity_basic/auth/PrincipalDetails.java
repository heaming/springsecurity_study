package com.study.springsecurity_basic.auth;

import com.study.springsecurity_basic.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/*
request "login" -> security 대신 진행 ->
success : security session을 만듦
    - Security ContextHolder(key)에 키값 저장
    - object type : Authentication Type
                    Authentication 안에 User정보가 있어야 함
                    UserObject Type : UserDetails Type
:: Security Session -> Authentication -> UserDetails(일반 로그인)/OAuth2User(OAuth 로그인)
::: Security Session -> Authentication -> PrincipalDetails -> UserDetails(일반 로그인)/OAuth2User(OAuth 로그인)
 */
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; // composition
    private Map<String, Object> attributes;

    // 일반 로그인 : UserDetails
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인 : OAuth2User
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 user의 권한 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // password 유지 기간
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 휴면 계정
    @Override
    public boolean isEnabled() {
        // curTime - loginTime >= 1year -> false
        return true;
    }

    //// OAuth2User
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
