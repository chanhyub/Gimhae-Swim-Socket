package com.alijas.gimhaeswimsocket.modules.common.exception.handler;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomRestException.class)
    public ProblemDetail customRestException(CustomRestException e) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
                e.getMessage()
        );
    }
}
