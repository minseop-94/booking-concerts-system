package com.booking.concerts.business.waitingQueue;

public enum WaitingQueueStatus {
        WAITING, // 대기 중
        ACTIVE, // Question - 대기열에서 처리 로직으로 넘어 갔는데, 상태를 관리해줄 필요가 있나?, A. 튕겼을 때, 돌아왔는데 끝나것도 아닌데, 대기열을 통과하지 못하니까?
        COMPLETED // 삭제 대신, 수정으로 삭제 처리
}
