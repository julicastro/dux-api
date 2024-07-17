package com.challenge.app.challenge.exception;

import com.challenge.app.challenge.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getLocalizedMessage());
        apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError();
        apiError.setMessage("La solicitud es inválida");
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<?> handlerObjectNotFoundException(HttpServletRequest request, TeamNotFoundException exception) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getLocalizedMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(TeamNotSavedException.class)
    public ResponseEntity<?> handlerObjectNotFoundException(HttpServletRequest request, TeamNotSavedException exception) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getLocalizedMessage());
        apiError.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CanNotLoginException.class)
    public ResponseEntity<?> handlerObjectNotFoundException(HttpServletRequest request, CanNotLoginException exception) {
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getLocalizedMessage());
        apiError.setCode(HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }





}
