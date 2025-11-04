package com.britishspokentime.service.factory;

import com.britishspokentime.domain.Time;
import com.britishspokentime.service.strategy.TimeFormatStrategy;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Factory for selecting the appropriate time formatting strategy.
 * Implements the Factory Pattern to encapsulate strategy selection logic.
 */
@Component
@RequiredArgsConstructor
public class TimeFormatStrategyFactory {

  private final List<TimeFormatStrategy> strategies;

  /**
   * Selects the appropriate strategy for formatting the given time.
   * Strategies are evaluated in priority order.
   *
   * @param time the time to format
   * @return the selected strategy
   * @throws IllegalStateException if no strategy can handle the time
   */
  public TimeFormatStrategy getStrategy(Time time) {
    return strategies.stream()
        .sorted(Comparator.comparingInt(TimeFormatStrategy::getPriority))
        .filter(strategy -> strategy.canHandle(time))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException(
            "No strategy found to handle time: " + time));
  }
}
