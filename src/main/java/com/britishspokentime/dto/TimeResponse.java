package com.britishspokentime.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for time conversion response.
 * Contains the original time and its British spoken form.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing the British spoken form of time")
public class TimeResponse {

  @Schema(description = "Original time input", example = "12:00")
  private String time;

  @Schema(description = "British spoken form of the time", example = "noon")
  private String spokenForm;
}
