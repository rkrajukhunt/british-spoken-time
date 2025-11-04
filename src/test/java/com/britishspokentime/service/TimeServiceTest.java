package com.britishspokentime.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.britishspokentime.dto.TimeRequest;
import com.britishspokentime.dto.TimeResponse;
import com.britishspokentime.model.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for TimeService.
 * Tests business logic orchestration and integration with TimeConverter.
 */
@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

  @Mock
  private TimeConverter timeConverter;

  @InjectMocks
  private TimeService timeService;

  @BeforeEach
  void setUp() {
    // Setup is handled by @Mock and @InjectMocks annotations
  }

  @ParameterizedTest
  @CsvSource({
      "00:00, midnight",
      "12:00, noon",
      "1:00, one o'clock",
      "7:30, half past seven",
      "9:45, quarter to ten"
  })
  void convertTime_withValidTime_shouldReturnCorrectResponse(String timeString,
      String expectedSpoken) {
    // Arrange
    TimeRequest request = new TimeRequest(timeString);
    Time time = new Time(
        Integer.parseInt(timeString.split(":")[0]),
        Integer.parseInt(timeString.split(":")[1])
    );

    when(timeConverter.parseTime(timeString)).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn(expectedSpoken);

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response).isNotNull();
    assertThat(response.getTime()).isEqualTo(timeString);
    assertThat(response.getSpokenForm()).isEqualTo(expectedSpoken);

    verify(timeConverter).parseTime(timeString);
    verify(timeConverter).convert(time);
  }

  @Test
  void convertTime_withMidnight_shouldReturnMidnight() {
    // Arrange
    TimeRequest request = new TimeRequest("00:00");
    Time time = new Time(0, 0);

    when(timeConverter.parseTime("00:00")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("midnight");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("00:00");
    assertThat(response.getSpokenForm()).isEqualTo("midnight");
  }

  @Test
  void convertTime_withNoon_shouldReturnNoon() {
    // Arrange
    TimeRequest request = new TimeRequest("12:00");
    Time time = new Time(12, 0);

    when(timeConverter.parseTime("12:00")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("noon");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("12:00");
    assertThat(response.getSpokenForm()).isEqualTo("noon");
  }

  @Test
  void convertTime_withQuarterPast_shouldReturnCorrectForm() {
    // Arrange
    TimeRequest request = new TimeRequest("4:15");
    Time time = new Time(4, 15);

    when(timeConverter.parseTime("4:15")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("quarter past four");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("4:15");
    assertThat(response.getSpokenForm()).isEqualTo("quarter past four");
  }

  @Test
  void convertTime_withHalfPast_shouldReturnCorrectForm() {
    // Arrange
    TimeRequest request = new TimeRequest("7:30");
    Time time = new Time(7, 30);

    when(timeConverter.parseTime("7:30")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("half past seven");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("7:30");
    assertThat(response.getSpokenForm()).isEqualTo("half past seven");
  }

  @Test
  void convertTime_withQuarterTo_shouldReturnCorrectForm() {
    // Arrange
    TimeRequest request = new TimeRequest("9:45");
    Time time = new Time(9, 45);

    when(timeConverter.parseTime("9:45")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("quarter to ten");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("9:45");
    assertThat(response.getSpokenForm()).isEqualTo("quarter to ten");
  }

  @Test
  void convertTime_withSpecialMinuteFormat_shouldReturnCorrectForm() {
    // Arrange
    TimeRequest request = new TimeRequest("6:32");
    Time time = new Time(6, 32);

    when(timeConverter.parseTime("6:32")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("six thirty two");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("6:32");
    assertThat(response.getSpokenForm()).isEqualTo("six thirty two");
  }

  @Test
  void convertTime_with24HourFormat_shouldReturnCorrectForm() {
    // Arrange
    TimeRequest request = new TimeRequest("23:59");
    Time time = new Time(23, 59);

    when(timeConverter.parseTime("23:59")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("one to twelve");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("23:59");
    assertThat(response.getSpokenForm()).isEqualTo("one to twelve");
  }

  @Test
  void convertTime_withInvalidFormat_shouldThrowException() {
    // Arrange
    TimeRequest request = new TimeRequest("25:00");

    when(timeConverter.parseTime("25:00"))
        .thenThrow(new IllegalArgumentException("Hour must be 0-23 and minute must be 0-59"));

    // Act & Assert
    assertThatThrownBy(() -> timeService.convertTime(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Hour must be 0-23 and minute must be 0-59");
  }

  @Test
  void convertTime_withNullTimeString_shouldThrowException() {
    // Arrange
    TimeRequest request = new TimeRequest(null);

    when(timeConverter.parseTime(null))
        .thenThrow(new IllegalArgumentException("Time string cannot be null or empty"));

    // Act & Assert
    assertThatThrownBy(() -> timeService.convertTime(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Time string cannot be null or empty");
  }

  @Test
  void convertTime_withEmptyTimeString_shouldThrowException() {
    // Arrange
    TimeRequest request = new TimeRequest("");

    when(timeConverter.parseTime(""))
        .thenThrow(new IllegalArgumentException("Time string cannot be null or empty"));

    // Act & Assert
    assertThatThrownBy(() -> timeService.convertTime(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Time string cannot be null or empty");
  }

  @Test
  void convertTime_withInvalidTimeFormat_shouldThrowException() {
    // Arrange
    TimeRequest request = new TimeRequest("invalid");

    when(timeConverter.parseTime("invalid"))
        .thenThrow(new IllegalArgumentException("Time must be in HH:mm format"));

    // Act & Assert
    assertThatThrownBy(() -> timeService.convertTime(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Time must be in HH:mm format");
  }

  @Test
  void convertTime_shouldCallTimeConverterMethods() {
    // Arrange
    TimeRequest request = new TimeRequest("12:30");
    Time time = new Time(12, 30);

    when(timeConverter.parseTime(anyString())).thenReturn(time);
    when(timeConverter.convert(any(Time.class))).thenReturn("twelve thirty");

    // Act
    timeService.convertTime(request);

    // Assert
    verify(timeConverter).parseTime("12:30");
    verify(timeConverter).convert(time);
  }

  @Test
  void convertTime_shouldReturnResponseWithSameTime() {
    // Arrange
    String timeString = "15:45";
    TimeRequest request = new TimeRequest(timeString);
    Time time = new Time(15, 45);

    when(timeConverter.parseTime(timeString)).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("quarter to four");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo(timeString);
  }

  @Test
  void convertTime_shouldReturnResponseWithSpokenForm() {
    // Arrange
    TimeRequest request = new TimeRequest("8:20");
    Time time = new Time(8, 20);
    String expectedSpoken = "twenty past eight";

    when(timeConverter.parseTime("8:20")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn(expectedSpoken);

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getSpokenForm()).isEqualTo(expectedSpoken);
  }

  @Test
  void convertTime_withLeadingZeros_shouldHandleCorrectly() {
    // Arrange
    TimeRequest request = new TimeRequest("01:05");
    Time time = new Time(1, 5);

    when(timeConverter.parseTime("01:05")).thenReturn(time);
    when(timeConverter.convert(time)).thenReturn("five past one");

    // Act
    TimeResponse response = timeService.convertTime(request);

    // Assert
    assertThat(response.getTime()).isEqualTo("01:05");
    assertThat(response.getSpokenForm()).isEqualTo("five past one");
  }
}
