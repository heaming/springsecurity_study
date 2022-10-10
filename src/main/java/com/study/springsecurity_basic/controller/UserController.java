package com.study.springsecurity_basic.controller;

import com.study.springsecurity_basic.exception.InvalidUserException;
import com.study.springsecurity_basic.model.User;
import com.study.springsecurity_basic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<HttpEntity> login(@RequestBody User user) {
        LOGGER.info("api/login : {}",user);
        User userEntity = userRepository.findByUsername(user.getUsername());

        try {

            if(userEntity == null) {
                throw new UsernameNotFoundException("no id : "+user.getUsername());
            }

            if(!passwordEncoder.matches(user.getPassword(), userEntity.getPassword())) {
                throw new InvalidUserException("invalid password : "+user.getPassword());
            }

            return new ResponseEntity<HttpEntity>(HttpStatus.OK);

        } catch(UsernameNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "username [ "+user.getUsername()+" ] not found : ", e);
        } catch (InvalidUserException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password : "+user.getUsername(), e);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "check your info", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unchecked exception",e);
        }
    }
}
