package com.booking.concerts.controller;

import com.booking.concerts.controller.dto.PaymentResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentsController.class)
class PaymentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testChargePoints() throws Exception {
        int userId = 11;
        int amount = 10000;
        int expectedBalance = 10000;


        MvcResult result = mockMvc.perform(post("/payments/users/{userId}", userId)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        // 응답 본문을 정수 타입으로 변환하여 검증
        int actualBalance = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertThat(actualBalance).isEqualTo(expectedBalance);
    }

    @Test
    void getBalance() throws Exception {
        int userId = 11;
        int expectedBalance = 100;

        MvcResult result = mockMvc.perform(get("/payments/users/{userId}/balance", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        // 응답 본문을 정수 타입으로 변환하여 검증
        // Question(loso): int 타입 체크하는데, 이런 방식으로 하는게 맞나? jsonPaht?
        int actualBalance = objectMapper.readValue(result.getResponse().getContentAsString(), Integer.class);
        assertThat(actualBalance).isEqualTo(expectedBalance);
    }

    @Test
    void processPayment() throws Exception {
        int reservationId = 99;
        PaymentResponseDTO expectedDTO = new PaymentResponseDTO();

        MvcResult result = mockMvc.perform(post("/payments/reservations/{reservationId}", reservationId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        PaymentResponseDTO actualDTO = objectMapper.readValue(result.getResponse().getContentAsString(), PaymentResponseDTO.class);
        assertThat(actualDTO).isEqualTo(expectedDTO); // DTO 객체 비교
    }
}