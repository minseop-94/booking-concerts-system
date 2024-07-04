package com.booking.concerts.controller;

import com.booking.concerts.controller.dto.ConcertOptionDTO;
import com.booking.concerts.controller.dto.SeatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concerts")
public class ConcertController {
    @GetMapping("/{concertId}/available-dates")
    public ResponseEntity<List<ConcertOptionDTO>> getAvailableDates(@PathVariable int concertId) {
        // 예약 가능 날짜 조회

        return ResponseEntity.ok(List.of(new ConcertOptionDTO()));
    }

    @GetMapping("/{concertId}/available-seats")
    public ResponseEntity<List<SeatDTO>> getAvailableSeats(@PathVariable int concertId) {
        // 예약 가능 좌석 조회

        return ResponseEntity.ok(List.of(new SeatDTO()));
    }
}
