package com.britishspokentime.domain;

import com.britishspokentime.constants.TimeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain model representing a time with hour and minute components.
 * This class encapsulates the time data and provides business logic methods.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Time {

  private int hour;
  private int minute;

  /**
   * Validates if the time components are within valid ranges.
   *
   * @return true if hour is 0-23 and minute is 0-59
   */
  public boolean isValid() {
    return hour >= TimeConstants.MIN_HOUR && hour <= TimeConstants.MAX_HOUR
        && minute >= TimeConstants.MIN_MINUTE && minute <= TimeConstants.MAX_MINUTE;
  }

  /**
   * Checks if the time represents midnight (00:00).
   *
   * @return true if time is 00:00
   */
  public boolean isMidnight() {
    return hour == TimeConstants.MIDNIGHT_HOUR && minute == TimeConstants.MIN_MINUTE;
  }

  /**
   * Checks if the time represents noon (12:00).
   *
   * @return true if time is 12:00
   */
  public boolean isNoon() {
    return hour == TimeConstants.NOON_HOUR && minute == TimeConstants.MIN_MINUTE;
  }

  /**
   * Checks if the time is exactly on the hour (minute is 0).
   *
   * @return true if minute is 0
   */
  public boolean isOclock() {
    return minute == TimeConstants.MIN_MINUTE;
  }

  /**
   * Gets the hour in a 12-hour format.
   *
   * @return hour in 12-hour format (1-12)
   */
  public int getTwelveHourFormat() {
    if (hour == TimeConstants.MIDNIGHT_HOUR) {
      return TimeConstants.MAX_TWELVE_HOUR;
    }
    return hour > TimeConstants.HOURS_IN_HALF_DAY
        ? hour - TimeConstants.HOURS_IN_HALF_DAY : hour;
  }

  /**
   * Gets the next hour for "to" time expressions.
   *
   * @return next hour in 12-hour format
   */
  public int getNextHour() {
    int nextHour = (hour + 1) % (TimeConstants.MAX_HOUR + 1);
    if (nextHour == TimeConstants.MIDNIGHT_HOUR) {
      return TimeConstants.MAX_TWELVE_HOUR;
    }
    return nextHour > TimeConstants.HOURS_IN_HALF_DAY
        ? nextHour - TimeConstants.HOURS_IN_HALF_DAY : nextHour;
  }
}
