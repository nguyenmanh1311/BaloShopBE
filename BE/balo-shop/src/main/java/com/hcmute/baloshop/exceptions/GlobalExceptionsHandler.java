package com.hcmute.baloshop.exceptions;

import com.hcmute.baloshop.models.ResponseObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionsHandler {
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseObject handleInvalidArgument2(MethodArgumentNotValidException exception) {
    Map<String, String> errorMap = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              errorMap.put(error.getField(), error.getDefaultMessage());
            });

    return new ResponseObject(HttpStatus.BAD_REQUEST, errorMap.toString());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handlerResourceNotFoundException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject(HttpStatus.NOT_FOUND, exception.getMessage(), null));
  }
  @ExceptionHandler(AlreadyExistException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handlerAlreadyExistException(
          RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
            .body(new ResponseObject(HttpStatus.ALREADY_REPORTED, exception.getMessage(), null));
  }

  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleResourceAlreadyExistsException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject(HttpStatus.OK, exception.getMessage(), null));
  }

  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseObject handleIllegalArgumentException(RuntimeException exception) {
    return new ResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(), null);
  }

  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders header,
      HttpStatus status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            (err) -> {
              String field = ((FieldError) err).getField();
              String message = err.getDefaultMessage();
              errors.put(field, message);
            });

    return new ResponseEntity<Object>(
        new ResponseObject(HttpStatus.BAD_REQUEST, exception.getMessage(), null),
        HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleParseDateException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject(HttpStatus.OK, exception.getMessage(), null));
  }

  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleFileException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject(HttpStatus.OK, exception.getMessage(), null));
  }

  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleInvalidCost(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(new ResponseObject(HttpStatus.OK, exception.getMessage(), null));
  }

  @ExceptionHandler(PersistenceException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handlePersistenceException(RuntimeException exception){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseObject(
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage(),
            null));
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleNumberFormatException(RuntimeException exception){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseObject(
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            null));
  }

  @ExceptionHandler(IOException.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  protected ResponseEntity<ResponseObject> handleIOException(RuntimeException exception){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new ResponseObject(
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage(),
            null));
  }

}
