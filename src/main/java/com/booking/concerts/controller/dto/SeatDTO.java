package com.booking.concerts.controller.dto;

import lombok.Data;

@Data
public class SeatDTO {
    private int seatNumber;
    private String grade;
    private int price;
}
