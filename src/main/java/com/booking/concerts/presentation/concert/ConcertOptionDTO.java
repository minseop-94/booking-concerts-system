package com.booking.concerts.concert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertOptionDTO {
    private int concertOptionID;
    private String host;
    private int totalSeatCount;
    private String venue;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime reservationStartTime;
}
