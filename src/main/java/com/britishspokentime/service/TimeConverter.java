package com.britishspokentime.service;

import com.britishspokentime.model.Time;

/**
 * Interface for converting time to British spoken form.
 * Defines the contract for time conversion implementations.
 */
public interface TimeConverter {

  /**
   * Converts a Time object to its British spoken form.
   *
   * @param time the time to convert
   * @return the British spoken form of the time
   */
  String convert(Time time);

  /**
   * Parses a time string in HH:mm format to a Time object.
   *
   * @param timeString the time string to parse
   * @return the parsed Time object
   * @throws IllegalArgumentException if the format is invalid
   */
  Time parseTime(String timeString);
}
