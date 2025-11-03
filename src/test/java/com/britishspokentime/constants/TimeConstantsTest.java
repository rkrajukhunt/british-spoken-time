package com.britishspokentime.constants;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for TimeConstants utility class.
 */
class TimeConstantsTest {

  @Test
  void constructor_shouldThrowException() throws NoSuchMethodException {
    // Arrange
    Constructor<TimeConstants> constructor = TimeConstants.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    // Act & Assert
    assertThatThrownBy(constructor::newInstance)
        .isInstanceOf(InvocationTargetException.class)
        .hasCauseInstanceOf(UnsupportedOperationException.class)
        .cause()
        .hasMessageContaining("Utility class");
  }

  @Test
  void timeSeparator_shouldBeColon() {
    assertThat(TimeConstants.TIME_SEPARATOR).isEqualTo(":");
  }

  @Test
  void timePartsCount_shouldBeTwo() {
    assertThat(TimeConstants.TIME_PARTS_COUNT).isEqualTo(2);
  }

  @Test
  void quarterMinutes_shouldBeFifteen() {
    assertThat(TimeConstants.QUARTER_MINUTES).isEqualTo(15);
  }

  @Test
  void halfHourMinutes_shouldBeThirty() {
    assertThat(TimeConstants.HALF_HOUR_MINUTES).isEqualTo(30);
  }

  @Test
  void threeQuarterMinutes_shouldBeFortyFive() {
    assertThat(TimeConstants.THREE_QUARTER_MINUTES).isEqualTo(45);
  }

  @Test
  void minutesInHour_shouldBeSixty() {
    assertThat(TimeConstants.MINUTES_IN_HOUR).isEqualTo(60);
  }

  @Test
  void minHour_shouldBeZero() {
    assertThat(TimeConstants.MIN_HOUR).isEqualTo(0);
  }

  @Test
  void maxHour_shouldBeTwentyThree() {
    assertThat(TimeConstants.MAX_HOUR).isEqualTo(23);
  }

  @Test
  void noonHour_shouldBeTwelve() {
    assertThat(TimeConstants.NOON_HOUR).isEqualTo(12);
  }

  @Test
  void midnightHour_shouldBeZero() {
    assertThat(TimeConstants.MIDNIGHT_HOUR).isEqualTo(0);
  }

  @Test
  void minMinute_shouldBeZero() {
    assertThat(TimeConstants.MIN_MINUTE).isEqualTo(0);
  }

  @Test
  void maxMinute_shouldBeFiftyNine() {
    assertThat(TimeConstants.MAX_MINUTE).isEqualTo(59);
  }

  @Test
  void pastThreshold_shouldBeThirty() {
    assertThat(TimeConstants.PAST_THRESHOLD).isEqualTo(30);
  }

  @Test
  void minTwelveHour_shouldBeOne() {
    assertThat(TimeConstants.MIN_TWELVE_HOUR).isEqualTo(1);
  }

  @Test
  void maxTwelveHour_shouldBeTwelve() {
    assertThat(TimeConstants.MAX_TWELVE_HOUR).isEqualTo(12);
  }

  @Test
  void hoursInHalfDay_shouldBeTwelve() {
    assertThat(TimeConstants.HOURS_IN_HALF_DAY).isEqualTo(12);
  }

  @Test
  void specialRange1Start_shouldBeThirtyTwo() {
    assertThat(TimeConstants.SPECIAL_RANGE_1_START).isEqualTo(32);
  }

  @Test
  void specialRange1End_shouldBeThirtyFour() {
    assertThat(TimeConstants.SPECIAL_RANGE_1_END).isEqualTo(34);
  }

  @Test
  void specialRange2Start_shouldBeThirtySeven() {
    assertThat(TimeConstants.SPECIAL_RANGE_2_START).isEqualTo(37);
  }

  @Test
  void specialRange2End_shouldBeThirtyNine() {
    assertThat(TimeConstants.SPECIAL_RANGE_2_END).isEqualTo(39);
  }

  @Test
  void thirty_shouldBeThirty() {
    assertThat(TimeConstants.THIRTY).isEqualTo(30);
  }

  @Test
  void forty_shouldBeForty() {
    assertThat(TimeConstants.FORTY).isEqualTo(40);
  }

  @Test
  void fifty_shouldBeFifty() {
    assertThat(TimeConstants.FIFTY).isEqualTo(50);
  }

  @Test
  void quarterMinutes_shouldBeLessThanHalfHour() {
    assertThat(TimeConstants.QUARTER_MINUTES).isLessThan(TimeConstants.HALF_HOUR_MINUTES);
  }

  @Test
  void halfHourMinutes_shouldBeLessThanThreeQuarter() {
    assertThat(TimeConstants.HALF_HOUR_MINUTES).isLessThan(TimeConstants.THREE_QUARTER_MINUTES);
  }

  @Test
  void threeQuarterMinutes_shouldBeLessThanMinutesInHour() {
    assertThat(TimeConstants.THREE_QUARTER_MINUTES).isLessThan(TimeConstants.MINUTES_IN_HOUR);
  }

  @Test
  void minHour_shouldBeLessThanMaxHour() {
    assertThat(TimeConstants.MIN_HOUR).isLessThan(TimeConstants.MAX_HOUR);
  }

  @Test
  void minMinute_shouldBeLessThanMaxMinute() {
    assertThat(TimeConstants.MIN_MINUTE).isLessThan(TimeConstants.MAX_MINUTE);
  }

  @Test
  void minTwelveHour_shouldBeLessThanMaxTwelveHour() {
    assertThat(TimeConstants.MIN_TWELVE_HOUR).isLessThan(TimeConstants.MAX_TWELVE_HOUR);
  }
}
