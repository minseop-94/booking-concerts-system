package com.booking.concerts.controller;

import com.booking.concerts.controller.dto.ReservationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void reserveSeat() throws Exception {
        int concertId = 11;
        int seatId = 22;
        ReservationDTO expectedDTO = new ReservationDTO(); // 예상되는 DTO 객체 생성

        MvcResult result = mockMvc.perform((RequestBuilder) put("/reservations/concerts/{concertId}/seats/{seatId}", concertId, seatId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ReservationDTO actualDTO = objectMapper.readValue(result.getResponse().getContentAsString(), ReservationDTO.class);
        assertThat(actualDTO).isEqualTo(expectedDTO); // DTO 객체 비교
    }
}