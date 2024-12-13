package com.capgemini.bookstore.error;

import com.capgemini.bookstore.generated.model.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException e) {
        LOG.error("Authentication exception: ", e);
        ErrorDto errorDto = new ErrorDto(HttpStatus.UNAUTHORIZED.value(), "Incorrect/missing credentials, please log in first.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorDto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        LOG.error("Authentication exception: ", e);
        ErrorDto errorDto = new ErrorDto(HttpStatus.FORBIDDEN.value(), "Insufficient permissions, you do not have the correct role for this action.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorDto);
    }
}
