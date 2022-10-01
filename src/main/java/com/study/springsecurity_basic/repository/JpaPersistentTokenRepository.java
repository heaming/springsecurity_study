package com.study.springsecurity_basic.repository;

import com.study.springsecurity_basic.model.PersistentLogins;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@RequiredArgsConstructor
@Repository
public class JpaPersistentTokenRepository implements PersistentTokenRepository {

    private Logger LOGGER = LoggerFactory.getLogger(JpaPersistentTokenRepository.class);

    private final PersistentLoginsRepository repository;



    @Override
    public void createNewToken(PersistentRememberMeToken token) {

        LOGGER.info("token: {}", token.toString());

        repository.save(PersistentLogins.from(token));
    }

    @Override
    public void updateToken(String series, String token, Date lastUsed) {
        repository.findBySeries(series)
                .ifPresent(persistentLogins -> {
                    persistentLogins.updateToken(token,new Timestamp(lastUsed.getTime()));
                    repository.save(persistentLogins);
                });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        return repository.findBySeries(series)
                .map(persistentLogins ->
                    new PersistentRememberMeToken(
                            persistentLogins.getUsername(),
                            persistentLogins.getSeries(),
                            persistentLogins.getToken(),
                            persistentLogins.getLastUsed()

                    ))
                .orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void removeUserTokens(String username) {
        repository.deleteAllInBatch(repository.findByUsername(username));
    }
}
