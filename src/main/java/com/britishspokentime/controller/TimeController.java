package com.britishspokentime.controller;

import com.britishspokentime.dto.TimeRequest;
import com.britishspokentime.dto.TimeResponse;
import com.britishspokentime.model.Time;
import com.britishspokentime.service.TimeConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for British Spoken Time conversion.
 * Provides endpoints to convert digital time to British spoken form.
 */
@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Time Conversion", description = "Endpoints for converting time to British spoken form")
public class TimeController {

    private final TimeConverter timeConverter;

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
        log.info("Converting time: {}", request.getTime());

        Time time = timeConverter.parseTime(request.getTime());
        String spokenForm = timeConverter.convert(time);

        log.info("Converted {} to '{}'", request.getTime(), spokenForm);

        TimeResponse response = new TimeResponse(request.getTime(), spokenForm);
        return ResponseEntity.ok(response);
    }

    /**
     * Converts a time string to British spoken form via query parameter.
     *
     * @param timeString the time in HH:mm format
     * @return the time response with British spoken form
     */
    @GetMapping("/convert")
    @Operation(
            summary = "Convert time to British spoken form (GET)",
            description = "Accepts time as query parameter in HH:mm format and returns the British spoken form"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully converted time",
                    content = @Content(schema = @Schema(implementation = TimeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid time format"
            )
    })
    public ResponseEntity<TimeResponse> convertTimeByQuery(
            @RequestParam(name = "time") String timeString) {
        log.info("Converting time via query: {}", timeString);

        Time time = timeConverter.parseTime(timeString);
        String spokenForm = timeConverter.convert(time);

        log.info("Converted {} to '{}'", timeString, spokenForm);

        TimeResponse response = new TimeResponse(timeString, spokenForm);
        return ResponseEntity.ok(response);
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
