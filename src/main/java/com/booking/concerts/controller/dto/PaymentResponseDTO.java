package com.booking.concerts.controller.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String paymentStatus;
    private String reservationStatus;
    private int balance;
}
