package com.booking.concerts.reservation;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private String reservationId;
    private String reservationStatus;
    private LocalDateTime expirationTime;
}
