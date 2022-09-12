package com.retail.rewards.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorModel errorDto = new ErrorModel();
        e.getBindingResult().getAllErrors().stream().limit(1).forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorDto.setErrorCode("400");
            errorDto.setFieldName(fieldName);
            errorDto.setMessage(errorMessage);
        });
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoOrderFoundException.class)
    public ResponseEntity<ErrorModel> handleNoOrderExceptionHandler(NoOrderFoundException e){
        ErrorModel error = new ErrorModel();
        error.setMessage(e.getMessage());
        error.setErrorCode("400");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
