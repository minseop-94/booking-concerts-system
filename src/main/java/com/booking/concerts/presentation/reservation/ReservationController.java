package com.booking.concerts.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @PutMapping("/concerts/{concertId}/seats/{seatId}")
    public ResponseEntity<ReservationDTO> reserveSeat(@PathVariable int concertId,
                                                      @PathVariable int seatId) {
        // 좌석 예약 로직

        return ResponseEntity.ok(new ReservationDTO());
    }
}
