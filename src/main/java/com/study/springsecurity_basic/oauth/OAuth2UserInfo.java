package com.study.springsecurity_basic.oauth;

public interface OAuth2UserInfo {
    public String getProviderId();
    public String getProvider();
    public String getEmail();
    public String getName();
}
