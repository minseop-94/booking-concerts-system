package com.booking.concerts.controller;

import com.booking.concerts.controller.dto.ConcertOptionDTO;
import com.booking.concerts.controller.dto.SeatDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAvailableDatesReturnConcertOptionDTO() throws Exception {
        // given
        int concertId = 1;
        List<ConcertOptionDTO> expectedDTOs = List.of(new ConcertOptionDTO());

        // when, then
        MvcResult result = mockMvc.perform(get("/concerts/{concertId}/available-dates", concertId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ConcertOptionDTO> actualLectures = objectMapper.readValue(responseBody, new TypeReference<List<ConcertOptionDTO>>() {});

        assertThat(actualLectures).isEqualTo(expectedDTOs);
    }

    @Test
    void getAvailableSeats() throws Exception {
        // given
        int concertId = 1;
        List<SeatDTO> expectedDTOs = List.of(new SeatDTO());

        // when, then
        MvcResult result = mockMvc.perform(get("/concerts/{concertId}/available-seats", concertId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<SeatDTO> actualLectures = objectMapper.readValue(responseBody, new TypeReference<List<SeatDTO>>() {});

        assertThat(actualLectures).isEqualTo(expectedDTOs);
    }
}