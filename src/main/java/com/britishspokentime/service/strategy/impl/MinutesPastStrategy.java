package com.britishspokentime.service.strategy.impl;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.domain.Time;
import com.britishspokentime.service.strategy.TimeFormatStrategy;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting "past" times (1-30 minutes).
 */
@Component
@RequiredArgsConstructor
public class MinutesPastStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    int minute = time.getMinute();
    return minute > TimeConstants.MIN_MINUTE && minute <= TimeConstants.PAST_THRESHOLD;
  }

  @Override
  public String format(Time time) {
    return converter.getMinuteWord(time.getMinute()) + " past "
        + converter.getHourWord(time.getTwelveHourFormat());
  }

  @Override
  public int getPriority() {
    return 7;
  }
}
