package com.study.springsecurity_basic.oauth;

import com.study.springsecurity_basic.auth.PrincipalDetails;
import com.study.springsecurity_basic.model.User;
import com.study.springsecurity_basic.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private static final Logger LOGGER = LogManager.getLogger(PrincipalOauth2UserService.class);

    @Autowired
    private UserRepository userRepository;
    
    // oauth2 login -> userRequest data 후처리
    // -getClientRegistration() : registrationId = provider
    // -getAccessToken().getTokenValue() : client가 발급받은 AccessToken
    // -super.loadUser(userReq).getAttribute() : provider로부터 회원프로필 받음
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        LOGGER.info("getClientRegistration: {}", userRequest.getClientRegistration());
        LOGGER.info("getAccessToken: {}", userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            LOGGER.info(oAuth2User.getAttributes());
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes());
        } else {
            LOGGER.info("[oauth.loadUser()]NON-SERVICE : We provide only google, naver login service");
        }

        LOGGER.info("get user:{}",oAuth2UserInfo);

        String provider = oAuth2UserInfo.getProvider(); // google, naver...
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; // google_10004787
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        // 중복 가입 여부 확인
        User userEntitiy = userRepository.findByUsername(username);

        if(userEntitiy == null) {
            userEntitiy = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .proverId(providerId)
                    .build();
            userRepository.save(userEntitiy);

            LOGGER.info("user saved: {}",userEntitiy);
        }

        LOGGER.info("user from db: {}",userEntitiy);

        return new PrincipalDetails(userEntitiy, oAuth2User.getAttributes());
    }
}
