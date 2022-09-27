package com.study.springsecurity_basic.oauth;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;
    private Map<String, Object> kakao_acount;
    private Map<String, Object> properties;

    public KakaoUserInfo(Map<String,Object> attributes) {
        this.attributes = attributes;
        this.kakao_acount = (Map) attributes.get("kakao_account");
        this.properties = (Map) attributes.get("properties");
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) kakao_acount.get("email");
    }

    @Override
    public String getName() {
        return (String) properties.get("nickname");
    }
}
