package com.study.springsecurity_basic.auth;

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
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId(); // google, naver...
        String providerId = oAuth2User.getAttribute("sub");
        String username = userRequest.getClientRegistration().getClientName()+"_"+providerId; // google_10004787
        String email = oAuth2User.getAttribute("email");
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
