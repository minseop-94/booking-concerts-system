package com.booking.concerts.business.waitingQueue;

import com.booking.concerts.infrastructure.waitingQueue.WaitingQueueRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// 대기열 관리
@Service
public class WaitingQueueService {
    private final WaitingQueueRepository waitingQueueRepository;
    private static final int MAX_CAPACITY = 50; // 최대 입장 가능 인원


    public WaitingQueueService(WaitingQueueRepository waitingQueueRepository) {
        this.waitingQueueRepository = waitingQueueRepository;
    }

    // 대기열 번호표 받고 줄 서 있음.
    public WaitingQueueStatus insertToWaitingQueue(WaitingQueue waitingQueue) {
        return this.waitingQueueRepository.save(waitingQueue).getStatus();
    }

    // 서버 입장 로직.
//    @Transactional
//    public boolean tryEnterQueue(Long userId) {
//        int currentWaitingCount = waitingQueueRepository.countByStatus(WaitingQueueStatus.WAITING);
//        if (currentWaitingCount >= MAX_CAPACITY) {
//            return false; // 대기열 꽉 찼을 경우 입장 불가
//        }
//        WaitingQueue waitingQueue = WaitingQueue.builder()
//                .userId(userId)
//                .status(WaitingQueueStatus.ACTIVE)
//                .enteredAt(LocalDateTime.now())
//                .build();
//        waitingQueueRepository.save(waitingQueue);
//        return true; // 대기열에 추가 성공
//    }

    // 주기적으로 입장 가능한 만큼 서버에 넣어줌.
    @Scheduled(fixedRate = 5000) // 5초 마다 실행
    @Transactional
    public void updateWaitingQueue() {
        // 현재 ACTIVE 상태인 사용자 수 조회
        int currentActiveCount = waitingQueueRepository.countByStatus(WaitingQueueStatus.ACTIVE);

        // 추가로 ACTIVE 상태로 변경할 수 있는 사용자 수 계산
        int availableSlots = MAX_CAPACITY - currentActiveCount;

        if (availableSlots > 0) {
            // WAITING 상태인 사용자들 중 가장 오래 기다린 순서대로 조회
            List<WaitingQueue> waitingUsers = waitingQueueRepository.findTopNByStatusOrderByEnteredAt(
                    WaitingQueueStatus.WAITING, availableSlots);

            for (WaitingQueue user : waitingUsers) {
                user.setStatus(WaitingQueueStatus.ACTIVE);
                waitingQueueRepository.save(user);
            }
        }

    }

    // 서버에 진입한지 5분이 지났는데, 대기열이 끝이 안나면, 계속 기다려 줄 수 없다. 제거하자.
    @Scheduled(fixedRate = 1000) // 1초마다 실행
    @Transactional
    public void deleteWaitingQueueIfOverStandardTime() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);

        List<WaitingQueue> expiredQueues = waitingQueueRepository.findByStatusAndActivatedAtBefore(
                WaitingQueueStatus.ACTIVE, cutoffTime);

        for (WaitingQueue queue : expiredQueues) {
            queue.setStatus(WaitingQueueStatus.COMPLETED);
            waitingQueueRepository.save(queue); // Jpa 이면 필요 없는 코드.
        }

        // 한 번에 삭제하는 방식을 선호한다면 아래 코드를 사용할 수 있습니다.
        // int deletedCount = waitingQueueRepository.deleteByStatusAndActivatedAtBefore(WaitingQueueStatus.ACTIVE, cutoffTime);
        // log.info("Deleted {} expired queues", deletedCount);
    }

}
