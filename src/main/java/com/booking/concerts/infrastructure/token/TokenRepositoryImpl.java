package com.booking.concerts.infrastructure.token;

import com.booking.concerts.business.token.Token;
import com.booking.concerts.business.token.TokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenRepositoryImpl implements TokenRepository {
    @Override
    public Token save(Token token) {
        return null;
    }

    @Override
    public Optional<Token> findByTokenValue(String previousTokenValue) {
        return Optional.empty();
    }

    @Override
    public Optional<Token> findByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public Token updateExpirationTime(String tokenValue, Date newExpiration) {
        return null;
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return false;
    }
}
