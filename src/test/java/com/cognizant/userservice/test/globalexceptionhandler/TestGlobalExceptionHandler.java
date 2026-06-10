package com.cognizant.userservice.test.globalexceptionhandler;

import com.cognizant.userservice.exceptions.EmailAlreadyExistsException;
import com.cognizant.userservice.exceptions.ResourceNotFoundException;
import com.cognizant.userservice.globalexceptionhandler.GlobalExceptionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Mock
    private ResourceNotFoundException resourceNotFoundException;

    @Mock
    private EmailAlreadyExistsException emailAlreadyExistsException;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void handleResourceNotFoundException_returns404WithMessage() {
        when(resourceNotFoundException.getMessage()).thenReturn("User not found");

        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFoundException(resourceNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    void handleEmailAlreadyExistsException_returns400WithMessage() {
        when(emailAlreadyExistsException.getMessage()).thenReturn("Email already exists");

        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFoundException(emailAlreadyExistsException);

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

        ResponseEntity<String> response = globalExceptionHandler.handleConstraintViolation(ex);

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

        ResponseEntity<String> response = globalExceptionHandler.handleConstraintViolation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name is required", response.getBody());
    }

    @Test
    void handleGenericException_returns400WithMessage() {
        Exception ex = mock(Exception.class);
        when(ex.getMessage()).thenReturn("Unexpected error");

        ResponseEntity<String> response = globalExceptionHandler.exceptionHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    @Test
    void handleGenericException_nullMessage_returnsNullBody() {
        Exception ex = mock(Exception.class);
        when(ex.getMessage()).thenReturn(null);

        ResponseEntity<String> response = globalExceptionHandler.exceptionHandler(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}
