package com.britishspokentime.service.strategy;

import com.britishspokentime.model.Time;
import org.springframework.stereotype.Component;

/**
 * Strategy for formatting noon (12:00).
 */
@Component
public class NoonStrategy implements TimeFormatStrategy {

  @Override
  public boolean canHandle(Time time) {
    return time.isNoon();
  }

  @Override
  public String format(Time time) {
    return "noon";
  }

  @Override
  public int getPriority() {
    return 2;
  }
}
