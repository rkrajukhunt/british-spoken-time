package com.britishspokentime.service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for NumberToWordConverter.
 */
class NumberToWordConverterTest {

  private NumberToWordConverter converter;

  @BeforeEach
  void setUp() {
    converter = new NumberToWordConverter();
  }

  @ParameterizedTest
  @CsvSource({
      "1, one",
      "2, two",
      "3, three",
      "4, four",
      "5, five",
      "6, six",
      "7, seven",
      "8, eight",
      "9, nine",
      "10, ten",
      "11, eleven",
      "12, twelve"
  })
  void getHourWord_validHours_shouldReturnCorrectWord(int hour, String expectedWord) {
    // Act
    String result = converter.getHourWord(hour);

    // Assert
    assertThat(result).isEqualTo(expectedWord);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 13, 14, 24, -1, 100})
  void getHourWord_invalidHours_shouldThrowException(int hour) {
    // Act & Assert
    assertThatThrownBy(() -> converter.getHourWord(hour))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Hour must be between 1 and 12");
  }

  @Test
  void getHourWord_boundaryLowerLimit_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getHourWord(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Hour must be between 1 and 12: 0");
  }

  @Test
  void getHourWord_boundaryUpperLimit_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getHourWord(13))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Hour must be between 1 and 12: 13");
  }

  @ParameterizedTest
  @CsvSource({
      "1, one",
      "2, two",
      "3, three",
      "5, five",
      "10, ten",
      "15, fifteen",
      "20, twenty",
      "21, twenty one",
      "25, twenty five",
      "29, twenty nine"
  })
  void getMinuteWord_validMinutes_shouldReturnCorrectWord(int minute, String expectedWord) {
    // Act
    String result = converter.getMinuteWord(minute);

    // Assert
    assertThat(result).isEqualTo(expectedWord);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 30, 31, 59, 60, -1})
  void getMinuteWord_invalidMinutes_shouldThrowException(int minute) {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteWord(minute))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Minute must be between 1 and 29");
  }

  @Test
  void getMinuteWord_boundaryZero_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteWord(0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getMinuteWord_boundaryThirty_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteWord(30))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getMinuteAsNumber_thirty_shouldReturnThirty() {
    // Act
    String result = converter.getMinuteAsNumber(30);

    // Assert
    assertThat(result).isEqualTo("thirty");
  }

  @ParameterizedTest
  @CsvSource({
      "31, thirty one",
      "32, thirty two",
      "33, thirty three",
      "34, thirty four",
      "35, thirty five",
      "36, thirty six",
      "37, thirty seven",
      "38, thirty eight",
      "39, thirty nine"
  })
  void getMinuteAsNumber_thirtyRange_shouldReturnCorrectWord(int minute, String expectedWord) {
    // Act
    String result = converter.getMinuteAsNumber(minute);

    // Assert
    assertThat(result).isEqualTo(expectedWord);
  }

  @Test
  void getMinuteAsNumber_forty_shouldReturnForty() {
    // Act
    String result = converter.getMinuteAsNumber(40);

    // Assert
    assertThat(result).isEqualTo("forty");
  }

  @ParameterizedTest
  @CsvSource({
      "41, forty one",
      "42, forty two",
      "43, forty three",
      "44, forty four",
      "45, forty five",
      "46, forty six",
      "47, forty seven",
      "48, forty eight",
      "49, forty nine"
  })
  void getMinuteAsNumber_fortyRange_shouldReturnCorrectWord(int minute, String expectedWord) {
    // Act
    String result = converter.getMinuteAsNumber(minute);

    // Assert
    assertThat(result).isEqualTo(expectedWord);
  }

  @Test
  void getMinuteAsNumber_fifty_shouldReturnFifty() {
    // Act
    String result = converter.getMinuteAsNumber(50);

    // Assert
    assertThat(result).isEqualTo("fifty");
  }

  @ParameterizedTest
  @CsvSource({
      "51, fifty one",
      "52, fifty two",
      "53, fifty three",
      "54, fifty four",
      "55, fifty five",
      "56, fifty six",
      "57, fifty seven",
      "58, fifty eight",
      "59, fifty nine"
  })
  void getMinuteAsNumber_fiftyRange_shouldReturnCorrectWord(int minute, String expectedWord) {
    // Act
    String result = converter.getMinuteAsNumber(minute);

    // Assert
    assertThat(result).isEqualTo(expectedWord);
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 29, 60, 61, -1})
  void getMinuteAsNumber_invalidMinutes_shouldThrowException(int minute) {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteAsNumber(minute))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid minute");
  }

  @Test
  void getMinuteAsNumber_boundaryBelowThirty_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteAsNumber(29))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid minute: 29");
  }

  @Test
  void getMinuteAsNumber_boundarySixty_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteAsNumber(60))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid minute: 60");
  }

  @Test
  void getMinuteAsNumber_boundaryNegative_shouldThrowException() {
    // Act & Assert
    assertThatThrownBy(() -> converter.getMinuteAsNumber(-1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getHourWord_allValidHoursOneToTwelve_shouldNotThrowException() {
    // Act & Assert
    for (int hour = 1; hour <= 12; hour++) {
      String result = converter.getHourWord(hour);
      assertThat(result).isNotNull().isNotEmpty();
    }
  }

  @Test
  void getMinuteWord_allValidMinutesOneToTwentyNine_shouldNotThrowException() {
    // Act & Assert
    for (int minute = 1; minute <= 29; minute++) {
      String result = converter.getMinuteWord(minute);
      assertThat(result).isNotNull();
    }
  }

  @Test
  void getMinuteAsNumber_allValidMinutesThirtyToFiftyNine_shouldNotThrowException() {
    // Act & Assert
    for (int minute = 30; minute <= 59; minute++) {
      String result = converter.getMinuteAsNumber(minute);
      assertThat(result).isNotNull().isNotEmpty();
    }
  }
}
