package com.booking.concerts.concert;

import lombok.Data;

@Data
public class SeatDTO {
    private int seatNumber;
    private String grade;
    private int price;
}
