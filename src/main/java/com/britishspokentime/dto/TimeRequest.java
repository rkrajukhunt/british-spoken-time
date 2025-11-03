package com.britishspokentime.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for time input requests.
 * Accepts time in HH:mm format (24-hour).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object containing time in HH:mm format")
public class TimeRequest {

    @NotBlank(message = "Time is required")
    @Pattern(
            regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Time must be in HH:mm format (00:00 to 23:59)"
    )
    @Schema(description = "Time in 24-hour format (HH:mm)", example = "12:00")
    private String time;
}
