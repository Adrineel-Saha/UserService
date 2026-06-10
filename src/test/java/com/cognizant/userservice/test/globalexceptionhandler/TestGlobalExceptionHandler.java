package com.cognizant.userservice.test.globalexceptionhandler;

import com.cognizant.userservice.exceptions.EmailAlreadyExistsException;
import com.cognizant.userservice.exceptions.ResourceNotFoundException;
import com.cognizant.userservice.globalexceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestGlobalExceptionHandler {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleResourceNotFoundException_returns404WithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("User not found");

        ResponseEntity<String> response = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleEmailAlreadyExistsException_returns400WithMessage() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Email already exists");

        ResponseEntity<String> response = handler.handleResourceNotFoundException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValidException_returns400WithJoinedMessages() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ObjectError error1 = new ObjectError("field1", "must not be blank");
        ObjectError error2 = new ObjectError("field2", "must be a valid email");
        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<String> response = handler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("must not be blank, must be a valid email", response.getBody());
    }

    @Test
    void handleMethodArgumentNotValidException_singleError_returnsMessage() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        ObjectError error = new ObjectError("name", "Name is required");
        when(bindingResult.getAllErrors()).thenReturn(List.of(error));
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<String> response = handler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name is required", response.getBody());
    }

    @Test
    void handleGenericException_returns400WithMessage() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<String> response = handler.exceptionHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    @Test
    void handleGenericException_nullMessage_returnsNullBody() {
        Exception ex = new Exception((String) null);

        ResponseEntity<String> response = handler.exceptionHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
