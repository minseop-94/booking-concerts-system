package com.booking.concerts.business.waitingQueue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
public class WaitingQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // autoIncrement 대기 순서 겸 id
    private Long userId; // 유저, 토큰 조회용
//    private int tokenId; // 토큰 조회용
    private WaitingQueueStatus status; // 대기 상태 확인 (activate, waiting, end..)
    private LocalDateTime enteredAt; // 대기열 진입 시간
    private LocalDateTime activatedAt; // 수정 시간. - 서버 진입 시간
}
