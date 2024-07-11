package com.booking.concerts.infrastructure.waitingQueue;

import com.booking.concerts.business.waitingQueue.WaitingQueue;
import com.booking.concerts.business.waitingQueue.WaitingQueueStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface WaitingQueueRepository {
    WaitingQueue save(WaitingQueue waitingQueue);

    int countByStatus(WaitingQueueStatus waitingQueueStatus);

    List<WaitingQueue> findTopNByStatusOrderByEnteredAt(WaitingQueueStatus waitingQueueStatus, int availableSlots);

    List<WaitingQueue> findByStatusAndActivatedAtBefore(WaitingQueueStatus waitingQueueStatus, LocalDateTime cutoffTime);
}
