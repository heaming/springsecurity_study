package com.study.springsecurity_basic.repository;

import com.study.springsecurity_basic.model.PersistentLogins;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersistentLoginsRepository extends JpaRepository<PersistentLogins, String> {

    Optional<PersistentLogins> findBySeries(final String series);

    List<PersistentLogins> findByUsername(final String username);

}
//https://shirohoo.github.io/spring/spring-security/2021-10-08-remember-me/
