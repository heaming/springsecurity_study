package com.study.springsecurity_basic.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "persistent_logins")
@Data
@Entity
@NoArgsConstructor
public class PersistentLogins implements Serializable {

    @Id
    private String series;
    private String username;
    private String token;
    @Column(name = "last_used")
    private Timestamp lastUsed;

    @Builder
    private PersistentLogins(final PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.token = token.getTokenValue();
        this.lastUsed = new Timestamp(token.getDate().getTime());
    }

    public static PersistentLogins from(final PersistentRememberMeToken token) {
        return new PersistentLogins(token);
    }

    public void updateToken(final String token, final Timestamp lastUsed) {
        this.token = token;
        this.lastUsed = lastUsed;
    }
}
