package com.britishspokentime.service;

import com.britishspokentime.dto.TimeRequest;
import com.britishspokentime.dto.TimeResponse;
import com.britishspokentime.domain.Time;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service layer for handling time conversion business logic.
 * Orchestrates the conversion of time strings to British spoken form.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TimeService {

  private final TimeConverter timeConverter;

  /**
   * Converts a time request to British spoken form.
   *
   * @param request the time request containing time in HH:mm format
   * @return the time response with British spoken form
   */
  public TimeResponse convertTime(TimeRequest request) {
    log.info("Converting time: {}", request.getTime());

    Time time = timeConverter.parseTime(request.getTime());
    String spokenForm = timeConverter.convert(time);

    log.info("Converted {} to '{}'", request.getTime(), spokenForm);

    return new TimeResponse(request.getTime(), spokenForm);
  }
}
