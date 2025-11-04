package com.britishspokentime.service.strategy.impl;

import com.britishspokentime.domain.Time;
import com.britishspokentime.service.strategy.TimeFormatStrategy;
import com.britishspokentime.service.util.NumberToWordConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting times on the hour (X:00).
 */
@Component
@RequiredArgsConstructor
public class OclockStrategy implements TimeFormatStrategy {

  private final NumberToWordConverter converter;

  @Override
  public boolean canHandle(Time time) {
    return time.isOclock();
  }

  @Override
  public String format(Time time) {
    return converter.getHourWord(time.getTwelveHourFormat()) + " o'clock";
  }

  @Override
  public int getPriority() {
    return 3;
  }
}
