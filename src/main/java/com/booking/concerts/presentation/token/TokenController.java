package com.booking.concerts.presentation.token;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {
    @PostMapping
    public ResponseEntity<Map<String, String>> issueToken(@RequestParam int userId) {
        // 토큰 발급 로직
        return ResponseEntity.ok(Map.of("token", "generated_token"));
    }
}
