package com.britishspokentime.service.strategy;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.model.Time;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting "to" times (31-59 minutes).
 * Fallback strategy with the lowest priority.
 */
@Component
@RequiredArgsConstructor
public class MinutesToStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    return time.getMinute() > TimeConstants.PAST_THRESHOLD;
  }

  @Override
  public String format(Time time) {
    return converter.getMinuteWord(TimeConstants.MINUTES_IN_HOUR - time.getMinute())
        + " to " + converter.getHourWord(time.getNextHour());
  }

  @Override
  public int getPriority() {
    return 9;
  }
}
