package com.alijas.gimhaeswimsocket.modules.common.exception.handler;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomRestException.class)
    public ResponseEntity<?> customRestException(CustomRestException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
