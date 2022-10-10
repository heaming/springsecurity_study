package com.study.springsecurity_basic.repository;

import com.study.springsecurity_basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* JpaRepository<T,ID>
    - @Repository 없어도 IoC : JpaRepository 상속
    - CRUD 함수를 JpaRepo가 가지고 있음
 */

public interface UserRepository extends JpaRepository<User, Long> {

    // findBy~
    // select * from user where ~ = ?
    public User findByUsername(String username);
}
