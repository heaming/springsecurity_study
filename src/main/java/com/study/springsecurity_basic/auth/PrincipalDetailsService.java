package com.study.springsecurity_basic.auth;

import com.study.springsecurity_basic.model.User;
import com.study.springsecurity_basic.oauth.PrincipalOauth2UserService;
import com.study.springsecurity_basic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 - SecurityConfig.loginProcessingUrl("/login")
    -> UserDetailsService Type으로 IoC된 loadUserByname() 실행

 :: security session(Authentication(UserDetails)
 */
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalOauth2UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntitiy = userRepository.findByUsername(username);

        LOGGER.info("user : {}", userEntitiy.toString());

        if(userEntitiy != null) {
            return new PrincipalDetails(userEntitiy);
        }
        return null;
    }
}
