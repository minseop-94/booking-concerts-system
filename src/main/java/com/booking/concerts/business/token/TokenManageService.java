package com.booking.concerts.business.token;

import com.booking.concerts.business.token.dto.TokenDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

// 토큰 관리
@Service
public class TokenManageService {
    private final TokenRepository tokenRepository;
    private final SecretKey secretKey;
    private final LocalDateTime tokenExpiration = LocalDateTime.now().plusHours(1); // 현재 시간 + 1시간


    @Autowired // SecretKey 빈 주입
    public TokenManageService (TokenRepository tokenRepository, SecretKey secretKey) {
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;
    }

    // 토큰 발급
    // TODO: HS256(암호화), JWT 공부하기.
    public TokenDTO generateToken(Long userId) {
        // JWT payload 설정 (Claim)
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId)); // 사용자 ID를 sub claim 설정
        Instant instant = tokenExpiration.atZone(ZoneId.of("Asia/Seoul")).toInstant();
        Date expirationDate = Date.from(instant); // LocalDateTime -> Date 변환: Jwt - LocalDateTime 지원 X


        // JWT 생성 및 서명
        String tokenValue = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate) // 만료 시간 설정
                .signWith(secretKey) // HS256 알고리즘으로 서명
                .compact();

        // 생성된 토큰을 db에 저장
        Token tokenEntity = Token.builder()
                .userId(userId)
                .tokenValue(tokenValue)
                .expirationDateTime(instant.atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime())
                .createdAt(LocalDateTime.now())
                .build();

        Token token = tokenRepository.save(tokenEntity);
        return token.toDTO();
    }

    // 토큰 조회: tokenValue -> Token Entity 조회
    public Token getTokenByTokenValue(String previousTokenValue) {
        Long userId = extractUserIdFromToken(previousTokenValue);
        return tokenRepository.findByUserId(userId)
                .orElseThrow(() -> new TokenNotFoundException("Token not found for user: " + userId));
    }

    public Token getTokenByUserId(Long userId) {
        Optional<Token> tokenOptional = tokenRepository.findByUserId(userId);
        return tokenOptional.orElseThrow(() -> new TokenNotFoundException("토큰을 찾을 수 없습니다."));
    }

    // 토큰에서 userId 추출
    private Long extractUserIdFromToken(String tokenValue) throws JwtException {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(tokenValue)
                .getBody();
        String userIdString = claims.getSubject();
        return Long.valueOf(userIdString);
    }

    // 토큰 검증
    public boolean validateToken(String previousTokenValue) {
        try {
            // 1. JWT 파싱 및 Claims 추출
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 토큰 생성 시 사용한 비밀 키와 동일해야 함
                    .build()
                    .parseClaimsJws(previousTokenValue)
                    .getBody();

            // 2. 만료 시간 확인
            Date expiration = claims.getExpiration();
            return expiration.after(new Date()); // 현재 시간과 비교

        } catch (JwtException e) {
            // 토큰 파싱 실패 또는 유효하지 않은 토큰 (예: 만료, 서명 오류 등)
            return false;
        }
    }

    // Question: try catch 구문이 아니라, globalHandlerException 을 사용해서 처리하려면, 모든 에러에 대해 다 써놓거나, 아님 어떤 에러가 발생할지 알아야 하나?
    // 토큰 만료 날짜 업데이트
    public Token updateTokenExpiration(String tokenValue) {
        // 1. JWT 파싱 및 Claims 추출
        Claims claims = getClaims(this.secretKey, tokenValue);

        // 3. 새로운 만료 시간 설정
        Date newExpiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        claims.setExpiration(newExpiration);

        // 4. 갱신된 토큰 생성
        String newTokenValue = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // 5. 데이터베이스에서 토큰 업데이트
        return tokenRepository.updateExpirationTime(tokenValue, newExpiration);
    }

    // 만료된 토큰 제거
    public void deleteExpiredToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByTokenValue(tokenValue);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            token.setDeletedAt(LocalDateTime.now());

            tokenRepository.save(token); // save() 호출 불필요 - Token 이 JpaEntity 라먄 -> TODO: JPA 영속성 컨텍스트(Persistence Context) 알아보기.

        } else {
            throw new TokenNotFoundException("Token not found for user");
        }
    }

    // JWT 파싱 및 Claims 추출 util 함수.
    public Claims getClaims(SecretKey secretKey, String tokenValue) {
        // 1. JWT 파싱 및 Claims 추출
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(tokenValue)
                .getBody();

        return claims;
    }

    public boolean isExistsUser(Long userId) {
        return tokenRepository.existsByUserId(userId);
    }
}
