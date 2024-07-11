package com.booking.concerts.business.token;

import com.booking.concerts.business.token.dto.TokenDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
// Question? 토큰 블랙리스트: 토큰 탈취 등의 보안 문제 발생 시, 특정 토큰을 무효화하기 위한 블랙리스트를 관리하는 것이 좋습니다. 이 경우, 토큰 검증 로직에서 블랙리스트에 있는 토큰인지 추가로 확인해야 합니다.
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 토큰 id
    private Long userId; // 누구인지 식별
    private String tokenValue; // 인증용 토큰 값
    private LocalDateTime expirationDateTime; // 토큰 만료 시간
    private LocalDateTime createdAt; // 토큰 생성 시간
    private LocalDateTime deletedAt; // 삭제 의미 시간.

    public TokenDTO toDTO() {
        return TokenDTO.builder()
                .userId(this.getUserId())
                .tokenValue(this.getTokenValue())
                .userId(this.getUserId())
                .build();
    }
}


