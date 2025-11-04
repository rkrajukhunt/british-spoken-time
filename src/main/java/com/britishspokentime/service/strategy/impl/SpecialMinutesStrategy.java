package com.britishspokentime.service.strategy.impl;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.domain.Time;
import com.britishspokentime.service.strategy.TimeFormatStrategy;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for special minute formats (32-34, 37-39).
 * These use the "hour minute" format instead of the "to" format.
 */
@Component
@RequiredArgsConstructor
public class SpecialMinutesStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    int minute = time.getMinute();
    return (minute >= TimeConstants.SPECIAL_RANGE_1_START
        && minute <= TimeConstants.SPECIAL_RANGE_1_END)
        || (minute >= TimeConstants.SPECIAL_RANGE_2_START
        && minute <= TimeConstants.SPECIAL_RANGE_2_END);
  }

  @Override
  public String format(Time time) {
    return converter.getHourWord(time.getTwelveHourFormat()) + " "
        + converter.getMinuteAsNumber(time.getMinute());
  }

  @Override
  public int getPriority() {
    return 8;
  }
}
