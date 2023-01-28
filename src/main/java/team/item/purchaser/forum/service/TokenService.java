package team.item.purchaser.forum.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.*;
import team.item.purchaser.forum.entity.Token;
import team.item.purchaser.forum.repository.TokenRepository;

import java.time.*;
import java.util.*;

@org.springframework.stereotype.Service(value = "tokenService")
public class TokenService implements Service<Token, String>, PersistentTokenRepository {

    @Autowired
    private TokenRepository repository;

    public boolean valid(Token token){
        return repository.existsById(token.getSeries());
    }

    @Override
    public JpaRepository<Token, String> repository() {
        return repository;
    }

    @Override
    public Token insert(Token entity) {
        Objects.requireNonNull(entity, "Entity could not be null");
        Objects.requireNonNull(entity.getSeries(), "Entity series could not be null");
        entity.setExpired(LocalDateTime.now().plusWeeks(2));
        entity.setSpawn(LocalDateTime.now());
        entity.setLast(LocalDateTime.now());
        return repository.saveAndFlush(entity);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        Token instance = new Token();
        BeanUtils.copyProperties(token, instance);
        this.insert(instance);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Optional<Token> optional = findById(series);
        optional.ifPresent((token)->{
            token.setValue(UUID.randomUUID().toString());
            token.setLast(LocalDateTime.from(lastUsed.toInstant()));
        });
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<Token> optional = findById(seriesId);
        return optional.map((token)-> new PersistentRememberMeToken(token.getUsername(), token.getSeries(), token.getValue(), Date.from(token.getLast().toInstant(ZoneOffset.ofHours(+8))))).orElse(null);
    }

    @Override
    public void removeUserTokens(String username) {
        if(repository.existsByUsername(username)){
            repository.deleteByUsername(username);
        }
    }
}
