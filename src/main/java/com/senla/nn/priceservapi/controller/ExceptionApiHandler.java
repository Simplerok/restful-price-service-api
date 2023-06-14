package com.senla.nn.priceservapi.controller;

import com.senla.nn.priceservapi.exception.AccessDeniedException;
import com.senla.nn.priceservapi.exception.AlreadyExistsException;
import com.senla.nn.priceservapi.exception.ErrorMessage;
import com.senla.nn.priceservapi.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
    @ExceptionHandler({AlreadyExistsException.class, IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ErrorMessage> alreadyExistsException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getLocalizedMessage()));
    }


    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    public ResponseEntity<ErrorMessage> accessDenied(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage("Access denied"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> expiredToken(ExpiredJwtException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage("Token was expired"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException exception){
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Authentication failed."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Method argument validation failed.");
        List<FieldError> errorList = exception.getBindingResult().getFieldErrors();
        errorMessage.setErrors(errorList.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> constraintViolationException(ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = new ErrorMessage("Entity fields validation failed");
        errorMessage.setErrors(exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        viol -> viol.getPropertyPath().toString(),
                        viol -> viol.getMessage())));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
