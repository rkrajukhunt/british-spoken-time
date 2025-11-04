package com.britishspokentime.service.strategy;

import com.britishspokentime.domain.Time;

/**
 * Strategy interface for different time formatting approaches.
 * Implements the Strategy Pattern for time conversion.
 */
public interface TimeFormatStrategy {

  /**
   * Checks if this strategy can handle the given time.
   *
   * @param time the time to check
   * @return true if this strategy applies
   */
  boolean canHandle(Time time);

  /**
   * Formats the time according to this strategy.
   *
   * @param time the time to format
   * @return the formatted time string
   */
  String format(Time time);

  /**
   * Returns the priority of this strategy (lower number = higher priority).
   *
   * @return the priority value
   */
  default int getPriority() {
    return 100;
  }
}
