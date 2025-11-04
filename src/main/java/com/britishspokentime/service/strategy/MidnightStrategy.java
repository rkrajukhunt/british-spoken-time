package com.britishspokentime.service.strategy;

import com.britishspokentime.domain.Time;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting midnight (00:00).
 */
@Component
public class MidnightStrategy implements TimeFormatStrategy {

  @Override
  public boolean canHandle(Time time) {
    return time.isMidnight();
  }

  @Override
  public String format(Time time) {
    return "midnight";
  }

  @Override
  public int getPriority() {
    return 1;
  }
}
