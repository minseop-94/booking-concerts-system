package com.booking.concerts.controller;

import com.booking.concerts.controller.dto.PaymentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    @PostMapping("/users/{userId}")
    public ResponseEntity<Integer> chargePoints(@PathVariable int userId,
                                                @RequestParam int amount) {
        // 잔액 충전 로직
        return ResponseEntity.ok(amount);
    }

    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<Integer> getBalance(@PathVariable int userId) {
        // 잔액 조회 로직

        return ResponseEntity.ok(100);
    }

    @PostMapping("/reservations/{reservationId}")
    public ResponseEntity<PaymentResponseDTO> processPayment(@PathVariable int reservationId) {
        // 결제 처리 로직

        return ResponseEntity.ok(new PaymentResponseDTO());
    }
}
