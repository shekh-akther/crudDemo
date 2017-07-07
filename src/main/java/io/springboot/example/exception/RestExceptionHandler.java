package io.springboot.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generic error handling component for Rest Controllers
 *
 * @author shekh akther
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult result = e.getBindingResult();
        ValidationResponse response = new ValidationResponse();
        response.setErrors(fromBindingErrors(result));
        log.error("Error processing incoming request: {}", request.getRequestURI(), e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity processHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("Error processing incoming request: {}", request.getRequestURI(), e);
        Map<String, String> errorMap = new HashMap();
        errorMap.put("error", "invalid request");
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity processException(Exception e, HttpServletRequest request) {
        log.error("Error processing incoming request: {}", request.getRequestURI(), e);
        Map<String, String> errorMap = new HashMap();
        errorMap.put("error", "Something went wrong. Please try again later.");
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    private List<String> fromBindingErrors(Errors errors) {
        return  errors.getFieldErrors().stream().map(fieldError -> String.format("%s.%s: %s", fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
                        .collect(Collectors.toList());
    }
}
