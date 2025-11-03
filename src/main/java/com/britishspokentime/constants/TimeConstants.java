package com.britishspokentime.constants;

/**
 * Constants for time conversion.
 * Centralizes all magic numbers and strings used in time processing.
 */
public final class TimeConstants {

  private TimeConstants() {
    throw new UnsupportedOperationException("Utility class");
  }

  // Time component separators
  public static final String TIME_SEPARATOR = ":";
  public static final int TIME_PARTS_COUNT = 2;

  // Special minute values
  public static final int QUARTER_MINUTES = 15;
  public static final int HALF_HOUR_MINUTES = 30;
  public static final int THREE_QUARTER_MINUTES = 45;
  public static final int MINUTES_IN_HOUR = 60;

  // Hour boundaries
  public static final int MIN_HOUR = 0;
  public static final int MAX_HOUR = 23;
  public static final int NOON_HOUR = 12;
  public static final int MIDNIGHT_HOUR = 0;

  // Minute boundaries
  public static final int MIN_MINUTE = 0;
  public static final int MAX_MINUTE = 59;
  public static final int PAST_THRESHOLD = 30;

  // 12-hour format
  public static final int MIN_TWELVE_HOUR = 1;
  public static final int MAX_TWELVE_HOUR = 12;
  public static final int HOURS_IN_HALF_DAY = 12;

  // Special minute ranges
  public static final int SPECIAL_RANGE_1_START = 32;
  public static final int SPECIAL_RANGE_1_END = 34;
  public static final int SPECIAL_RANGE_2_START = 37;
  public static final int SPECIAL_RANGE_2_END = 39;

  // Number conversion boundaries
  public static final int THIRTY = 30;
  public static final int FORTY = 40;
  public static final int FIFTY = 50;
}
