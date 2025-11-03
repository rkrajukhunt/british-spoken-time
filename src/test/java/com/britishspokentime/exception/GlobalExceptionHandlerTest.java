package com.britishspokentime.exception;

import com.britishspokentime.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for GlobalExceptionHandler.
 */
@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

  @InjectMocks
  private GlobalExceptionHandler globalExceptionHandler;

  private WebRequest webRequest;

  @BeforeEach
  void setUp() {
    webRequest = mock(WebRequest.class);
    when(webRequest.getDescription(false)).thenReturn("uri=/api/test");
  }

  @Test
  void handleValidationExceptions_shouldReturnBadRequest() {
    // Arrange
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);

    FieldError fieldError1 = new FieldError("timeRequest", "time", "Time is required");
    FieldError fieldError2 = new FieldError("timeRequest", "format", "Format must be valid");
    List<FieldError> fieldErrors = Arrays.asList(fieldError1, fieldError2);

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo(400);
    assertThat(response.getBody().getError()).isEqualTo("Validation Error");
    assertThat(response.getBody().getMessage()).contains("Time is required", "Format must be valid");
    assertThat(response.getBody().getPath()).isEqualTo("/api/test");
    assertThat(response.getBody().getTimestamp()).isNotNull();
  }

  @Test
  void handleValidationExceptions_singleFieldError_shouldReturnBadRequest() {
    // Arrange
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);

    FieldError fieldError = new FieldError("timeRequest", "time", "Invalid time format");
    List<FieldError> fieldErrors = Arrays.asList(fieldError);

    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid time format");
  }

  @Test
  void handleIllegalArgumentException_shouldReturnBadRequest() {
    // Arrange
    IllegalArgumentException exception = new IllegalArgumentException("Invalid hour: 25");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo(400);
    assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    assertThat(response.getBody().getMessage()).isEqualTo("Invalid hour: 25");
    assertThat(response.getBody().getPath()).isEqualTo("/api/test");
    assertThat(response.getBody().getTimestamp()).isNotNull();
  }

  @Test
  void handleGlobalException_shouldReturnInternalServerError() {
    // Arrange
    Exception exception = new RuntimeException("Unexpected database error");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo(500);
    assertThat(response.getBody().getError()).isEqualTo("Internal Server Error");
    assertThat(response.getBody().getMessage()).isEqualTo("An unexpected error occurred. Please try again later.");
    assertThat(response.getBody().getPath()).isEqualTo("/api/test");
    assertThat(response.getBody().getTimestamp()).isNotNull();
  }

  @Test
  void handleGlobalException_withNullPointerException_shouldReturnInternalServerError() {
    // Arrange
    Exception exception = new NullPointerException("Null value encountered");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getStatus()).isEqualTo(500);
  }

  @Test
  void handleGlobalException_withArithmeticException_shouldReturnInternalServerError() {
    // Arrange
    Exception exception = new ArithmeticException("Division by zero");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGlobalException(
        exception, webRequest);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isNotNull();
  }
}
