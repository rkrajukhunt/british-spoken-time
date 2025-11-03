package com.britishspokentime.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Unit tests for the Time domain model.
 */
class TimeTest {

  @ParameterizedTest
  @CsvSource({
      "0, 0, true",
      "12, 0, true",
      "23, 59, true",
      "12, 30, true"
  })
  void testIsValid_validTimes(int hour, int minute, boolean expected) {
    Time time = new Time(hour, minute);
    assertEquals(expected, time.isValid());
  }

  @ParameterizedTest
  @CsvSource({
      "-1, 0, false",
      "24, 0, false",
      "12, 60, false",
      "12, -1, false"
  })
  void testIsValid_invalidTimes(int hour, int minute, boolean expected) {
    Time time = new Time(hour, minute);
    assertEquals(expected, time.isValid());
  }

  @Test
  void testIsMidnight_trueForZeroZero() {
    Time time = new Time(0, 0);
    assertTrue(time.isMidnight());
  }

  @Test
  void testIsMidnight_falseForNonMidnight() {
    Time time = new Time(0, 1);
    assertFalse(time.isMidnight());
  }

  @Test
  void testIsNoon_trueForTwelveZero() {
    Time time = new Time(12, 0);
    assertTrue(time.isNoon());
  }

  @Test
  void testIsNoon_falseForNonNoon() {
    Time time = new Time(12, 1);
    assertFalse(time.isNoon());
  }

  @Test
  void testIsOclock_trueWhenMinuteIsZero() {
    Time time = new Time(5, 0);
    assertTrue(time.isOclock());
  }

  @Test
  void testIsOclock_falseWhenMinuteIsNotZero() {
    Time time = new Time(5, 30);
    assertFalse(time.isOclock());
  }

  @ParameterizedTest
  @CsvSource({
      "0, 12",
      "1, 1",
      "12, 12",
      "13, 1",
      "23, 11"
  })
  void testGetTwelveHourFormat(int hour, int expected) {
    Time time = new Time(hour, 0);
    assertEquals(expected, time.getTwelveHourFormat());
  }

  @ParameterizedTest
  @CsvSource({
      "0, 1",
      "11, 12",
      "12, 1",
      "23, 12"
  })
  void testGetNextHour(int hour, int expected) {
    Time time = new Time(hour, 30);
    assertEquals(expected, time.getNextHour());
  }
}
