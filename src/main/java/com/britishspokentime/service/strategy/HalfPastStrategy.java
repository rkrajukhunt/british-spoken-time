package com.britishspokentime.service.strategy;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.model.Time;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting half past times (X:30).
 */
@Component
@RequiredArgsConstructor
public class HalfPastStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    return time.getMinute() == TimeConstants.HALF_HOUR_MINUTES;
  }

  @Override
  public String format(Time time) {
    return "half past " + converter.getHourWord(time.getTwelveHourFormat());
  }

  @Override
  public int getPriority() {
    return 5;
  }
}
