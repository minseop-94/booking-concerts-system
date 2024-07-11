package com.booking.concerts.business.token;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
public interface TokenRepository {
    // 토큰 저장
    Token save(Token token);

    Optional<Token> findByTokenValue(String previousTokenValue);

    Optional<Token> findByUserId(Long userId);

    Token updateExpirationTime(String tokenValue, Date newExpiration);

    boolean existsByUserId(Long userId);
}
