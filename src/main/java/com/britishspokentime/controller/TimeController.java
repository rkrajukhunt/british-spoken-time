package com.britishspokentime.controller;

import com.britishspokentime.dto.TimeRequest;
import com.britishspokentime.dto.TimeResponse;
import com.britishspokentime.service.TimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for British Spoken Time conversion.
 * Provides endpoints to convert digital time to British spoken form.
 */
@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
@Tag(name = "Time Conversion",
    description = "Endpoints for converting time to British spoken form")
public class TimeController {

  private final TimeService timeService;

  /**
   * Converts a time string to British spoken form.
   *
   * @param request the time request containing time in HH:mm format
   * @return the time response with British spoken form
   */
  @PostMapping("/convert")
  @Operation(
      summary = "Convert time to British spoken form",
      description = "Accepts time in HH:mm format (24-hour) and returns the British spoken form"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Successfully converted time",
          content = @Content(schema = @Schema(implementation = TimeResponse.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Invalid time format or validation error"
      )
  })
  public ResponseEntity<TimeResponse> convertTime(@Valid @RequestBody TimeRequest request) {
    return ResponseEntity.ok(timeService.convertTime(request));
  }

  /**
   * Health check endpoint.
   *
   * @return a simple status message
   */
  @GetMapping("/health")
  @Operation(summary = "Health check", description = "Returns service health status")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("British Spoken Time API is running");
  }
}
