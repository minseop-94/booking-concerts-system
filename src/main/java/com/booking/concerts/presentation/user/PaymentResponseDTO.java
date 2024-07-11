package com.booking.concerts.user;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String paymentStatus;
    private String reservationStatus;
    private int balance;
}
