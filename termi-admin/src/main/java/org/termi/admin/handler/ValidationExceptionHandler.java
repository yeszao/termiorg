package org.termi.admin.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice("org.termi")
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        e.getConstraintViolations().forEach(error
                -> errors.add(String.format("%s %s", error.getPropertyPath().toString(), error.getMessage())));
        return ResponseEntity.badRequest().body(errors);
    }
}