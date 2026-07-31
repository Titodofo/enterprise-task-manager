package es.isaac.etm.enterprise_task_manager.controller;

import es.isaac.etm.enterprise_task_manager.exception.EmpleadoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmpleadoNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEmpleadoNotFoundException(EmpleadoNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("id", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

}
