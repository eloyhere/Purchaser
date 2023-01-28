package team.item.purchaser.forum.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "token")
@Getter
@Setter
@ToString
public class Token implements org.springframework.security.core.token.Token{
    private String username;

    @Id
    @Column(nullable = false)
    private String series;

    private String value;

    private LocalDateTime last;

    private LocalDateTime spawn;

    private LocalDateTime expired;

    public Token(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return series != null && Objects.equals(series, token.series);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String getKey() {
        return series;
    }

    @Override
    public long getKeyCreationTime() {
        return this.spawn.getNano();
    }

    @Override
    public String getExtendedInformation() {
        return null;
    }
}