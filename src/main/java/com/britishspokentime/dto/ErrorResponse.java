package com.britishspokentime.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for error responses.
 * Provides consistent error information to API consumers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response object")
public class ErrorResponse {

  @Schema(description = "Timestamp when the error occurred")
  private LocalDateTime timestamp;

  @Schema(description = "HTTP status code")
  private int status;

  @Schema(description = "Error type/title")
  private String error;

  @Schema(description = "Detailed error message")
  private String message;

  @Schema(description = "API path where error occurred")
  private String path;
}
