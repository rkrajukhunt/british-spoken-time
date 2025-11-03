package com.britishspokentime.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.britishspokentime.dto.TimeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for TimeController REST endpoints.
 * Tests the full request-response cycle including validation and error handling.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TimeControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  // ========== POST /api/v1/time/convert Tests ==========

  @ParameterizedTest
  @CsvSource({
      "00:00, midnight",
      "12:00, noon",
      "1:00, one o'clock",
      "2:05, five past two",
      "3:10, ten past three",
      "4:15, quarter past four",
      "5:20, twenty past five",
      "6:25, twenty five past six",
      "6:32, six thirty two",
      "7:30, half past seven",
      "7:35, twenty five to eight",
      "8:40, twenty to nine",
      "9:45, quarter to ten",
      "10:50, ten to eleven",
      "11:55, five to twelve"
  })
  void testConvertTime_validTimes(String time, String expectedSpoken) throws Exception {
    TimeRequest request = new TimeRequest(time);

    mockMvc.perform(post("/api/v1/time/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.time", is(time)))
        .andExpect(jsonPath("$.spokenForm", is(expectedSpoken)));
  }

  @Test
  void testConvertTime_nullTime() throws Exception {
    TimeRequest request = new TimeRequest(null);

    mockMvc.perform(post("/api/v1/time/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.error", is("Validation Error")));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  ", "25:00", "12:60", "ab:cd", "12", "1234"})
  void testConvertTime_invalidFormat(String invalidTime) throws Exception {
    TimeRequest request = new TimeRequest(invalidTime);

    mockMvc.perform(post("/api/v1/time/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400)));
  }

  // ========== GET /api/v1/time/convert Tests ==========

  @ParameterizedTest
  @CsvSource({
      "00:00, midnight",
      "12:00, noon",
      "1:00, one o'clock",
      "7:30, half past seven",
      "9:45, quarter to ten"
  })
  void testConvertTimeByQuery_validTimes(String time, String expectedSpoken) throws Exception {
    mockMvc.perform(get("/api/v1/time/convert")
            .param("time", time))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.time", is(time)))
        .andExpect(jsonPath("$.spokenForm", is(expectedSpoken)));
  }

  @ParameterizedTest
  @ValueSource(strings = {"25:00", "12:60", "invalid", ""})
  void testConvertTimeByQuery_invalidFormat(String invalidTime) throws Exception {
    mockMvc.perform(get("/api/v1/time/convert")
            .param("time", invalidTime))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.error", is("Bad Request")));
  }

  // ========== Health Check Test ==========

  @Test
  void testHealth() throws Exception {
    mockMvc.perform(get("/api/v1/time/health"))
        .andExpect(status().isOk())
        .andExpect(content().string("British Spoken Time API is running"));
  }

  // ========== Edge Cases ==========

  @Test
  void testConvertTime_24HourFormat() throws Exception {
    TimeRequest request = new TimeRequest("23:59");

    mockMvc.perform(post("/api/v1/time/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.spokenForm", is("one to twelve")));
  }

  @Test
  void testConvertTime_leadingZeros() throws Exception {
    TimeRequest request = new TimeRequest("01:05");

    mockMvc.perform(post("/api/v1/time/convert")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.spokenForm", is("five past one")));
  }

  @Test
  void testConvertTime_allSpecialMinuteFormats() throws Exception {
    String[] times = {"6:32", "6:33", "6:34", "6:37", "6:38", "6:39"};
    String[] expected = {
        "six thirty two",
        "six thirty three",
        "six thirty four",
        "six thirty seven",
        "six thirty eight",
        "six thirty nine"
    };

    for (int i = 0; i < times.length; i++) {
      TimeRequest request = new TimeRequest(times[i]);

      mockMvc.perform(post("/api/v1/time/convert")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.spokenForm", is(expected[i])));
    }
  }
}
