package com.britishspokentime.service.strategy;

import com.britishspokentime.constants.TimeConstants;
import com.britishspokentime.domain.Time;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting quarter past times (X:15).
 */
@Component
@RequiredArgsConstructor
public class QuarterPastStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    return time.getMinute() == TimeConstants.QUARTER_MINUTES;
  }

  @Override
  public String format(Time time) {
    return "quarter past " + converter.getHourWord(time.getTwelveHourFormat());
  }

  @Override
  public int getPriority() {
    return 4;
  }
}
