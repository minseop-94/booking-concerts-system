package com.booking.concerts.application;

import com.booking.concerts.business.token.TokenManageService;
import com.booking.concerts.business.token.dto.TokenDTO;
import com.booking.concerts.business.waitingQueue.WaitingQueue;
import com.booking.concerts.business.waitingQueue.WaitingQueueService;
import com.booking.concerts.business.waitingQueue.WaitingQueueStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TokenWaitingQueueFacade {
    // useCase - 사용자 서버 가용성 진입 가능 여부
        // 1. 토큰 발급
        // 2. (발급된) 토큰의 유효성 검사
        // 3. 대기열 상태 확인 : 진입 가능?
        // 3. 대기열 상태 업데이트
    private TokenManageService tokenManageService;
    private WaitingQueueService waitingQueueService;

    public TokenWaitingQueueFacade() {}
    public TokenWaitingQueueFacade(TokenManageService tokenManageService, WaitingQueueService waitingQueueService) {
        this.tokenManageService = tokenManageService;
        this.waitingQueueService = waitingQueueService;
    }

    // (useCase) 유저를 대기열로 관리하기 위한 저장.
    // 1. 유저가 서버에 진입 했을 때, 토큰을 발급한다.
    // 2. 토큰을 대기열에 저장한다.
    public String publishTokenAndAddToWaitingQueue(Long userId) {
        // 토큰 조회해서 처음 들어온 것인지 확인.
        boolean isExistsUser = tokenManageService.isExistsUser(userId);
        if (isExistsUser && tokenManageService.validateToken(tokenManageService.getTokenByUserId(userId).getTokenValue())) {
            throw new RuntimeException("이미 존재하는 유저입니다.");
        }

        // 처음 들어온 것은 아니나, 토큰이 만료된 경우, 재발급

        TokenDTO tokenDTO = this.tokenManageService.generateToken(userId);
        WaitingQueueStatus status = this.waitingQueueService.insertToWaitingQueue(WaitingQueue.builder()
                .userId(userId)
                .status(WaitingQueueStatus.WAITING)
                .enteredAt(LocalDateTime.now())
                .build());

        return status.toString();
    }


}
