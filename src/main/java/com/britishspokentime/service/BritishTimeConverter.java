package com.britishspokentime.service;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.model.Time;
import com.britishspokentime.service.factory.TimeFormatStrategyFactory;
import com.britishspokentime.service.strategy.TimeFormatStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of TimeConverter for British English time expressions.
 * Uses Strategy Pattern with Factory Pattern for flexible time formatting.
 */
@Service
@RequiredArgsConstructor
public class BritishTimeConverter implements TimeConverter {

  private final TimeFormatStrategyFactory strategyFactory;

  @Override
  public String convert(Time time) {
    if (time == null || !time.isValid()) {
      throw new IllegalArgumentException("Invalid time provided");
    }

    TimeFormatStrategy strategy = strategyFactory.getStrategy(time);
    return strategy.format(time);
  }

  @Override
  public Time parseTime(String timeString) {
    if (timeString == null || timeString.isBlank()) {
      throw new IllegalArgumentException("Time string cannot be null or empty");
    }

    String[] parts = timeString.split(TimeConstants.TIME_SEPARATOR);
    if (parts.length != TimeConstants.TIME_PARTS_COUNT) {
      throw new IllegalArgumentException("Time must be in HH:mm format");
    }

    try {
      int hour = Integer.parseInt(parts[0]);
      int minute = Integer.parseInt(parts[1]);
      Time time = new Time(hour, minute);

      if (!time.isValid()) {
        throw new IllegalArgumentException(
            String.format("Hour must be %d-%d and minute must be %d-%d",
                TimeConstants.MIN_HOUR, TimeConstants.MAX_HOUR,
                TimeConstants.MIN_MINUTE, TimeConstants.MAX_MINUTE));
      }

      return time;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid time format: " + timeString, e);
    }
  }
}
