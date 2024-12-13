package com.capgemini.bookstore.error;

import com.capgemini.bookstore.generated.model.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order
public class GeneralExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception e) {
        LOG.error("Generic exception: ", e);
        ErrorDto errorDto = new ErrorDto(500, "Internal Server Error, please contact your administrator.");
        return ResponseEntity.internalServerError()
                .body(errorDto);
    }
}
