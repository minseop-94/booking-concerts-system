package com.booking.concerts.business.token.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private Long tokenId;
    private Long userId;
    private String tokenValue;
}
