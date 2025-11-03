package com.britishspokentime.service.util;

import com.britishspokentime.constants.TimeConstants;
import org.springframework.stereotype.Component;

/**
 * Utility class for converting numbers to their word representations.
 * Follows Single Responsibility Principle.
 */
@Component
public class NumberToWordConverter {

  private static final String[] HOURS = {
      "twelve", "one", "two", "three", "four", "five",
      "six", "seven", "eight", "nine", "ten", "eleven",
      "twelve", "one", "two", "three", "four", "five",
      "six", "seven", "eight", "nine", "ten", "eleven"
  };

  private static final String[] MINUTES = {
      "", "one", "two", "three", "four", "five",
      "six", "seven", "eight", "nine", "ten",
      "eleven", "twelve", "thirteen", "fourteen", "fifteen",
      "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
      "twenty one", "twenty two", "twenty three", "twenty four", "twenty five",
      "twenty six", "twenty seven", "twenty eight", "twenty nine"
  };

  /**
   * Gets the word representation of an hour (1-12).
   *
   * @param hour the hour to convert (1-12)
   * @return the word representation of the hour
   * @throws IllegalArgumentException if hour is not between 1 and 12
   */
  public String getHourWord(int hour) {
    if (hour < TimeConstants.MIN_TWELVE_HOUR || hour > TimeConstants.MAX_TWELVE_HOUR) {
      throw new IllegalArgumentException(
          String.format("Hour must be between %d and %d: %d",
              TimeConstants.MIN_TWELVE_HOUR, TimeConstants.MAX_TWELVE_HOUR, hour));
    }
    return HOURS[hour];
  }

  /**
   * Gets the word representation of a minute (1-29).
   *
   * @param minute the minute to convert (1-29)
   * @return the word representation of the minute
   * @throws IllegalArgumentException if minute is not between 1 and 29
   */
  public String getMinuteWord(int minute) {
    if (minute <= TimeConstants.MIN_MINUTE || minute >= TimeConstants.PAST_THRESHOLD) {
      throw new IllegalArgumentException(
          String.format("Minute must be between %d and %d: %d",
              TimeConstants.MIN_MINUTE + 1, TimeConstants.PAST_THRESHOLD - 1, minute));
    }
    return MINUTES[minute];
  }

  /**
   * Gets the word representation of a minute as a number (30-59).
   *
   * @param minute the minute to convert (30-59)
   * @return the word representation of the minute as a number
   * @throws IllegalArgumentException if minute is not between 30 and 59
   */
  public String getMinuteAsNumber(int minute) {
    if (minute < TimeConstants.THIRTY || minute >= TimeConstants.MINUTES_IN_HOUR) {
      throw new IllegalArgumentException("Invalid minute: " + minute);
    }

    if (minute < TimeConstants.FORTY) {
      return "thirty" + (minute == TimeConstants.THIRTY ? ""
          : " " + MINUTES[minute - TimeConstants.THIRTY]);
    } else if (minute < TimeConstants.FIFTY) {
      return "forty" + (minute == TimeConstants.FORTY ? ""
          : " " + MINUTES[minute - TimeConstants.FORTY]);
    } else {
      return "fifty" + (minute == TimeConstants.FIFTY ? ""
          : " " + MINUTES[minute - TimeConstants.FIFTY]);
    }
  }
}
