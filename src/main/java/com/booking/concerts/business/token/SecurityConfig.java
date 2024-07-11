package com.booking.concerts.business.token;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {
    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = "this is a competition bro, keep going till die".getBytes(StandardCharsets.UTF_8); // 256비트 비밀 키 문자열
        return Keys.hmacShaKeyFor(keyBytes); // 비밀 키 생성
    }
}
