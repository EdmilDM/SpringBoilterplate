package com.edmil.boilerplate.exception;

import com.edmil.boilerplate.exception.customexceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@ControllerAdvice
public class APIExceptionHandler {

    private ExceptionReturnObject exceptionReturnObjectObj = new ExceptionReturnObject();

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundExceptions(Exception ex, WebRequest request){

        exceptionReturnObjectObj.setMessage(ex.getLocalizedMessage());
        exceptionReturnObjectObj.setError(ex.getClass().getCanonicalName());
        exceptionReturnObjectObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        exceptionReturnObjectObj.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionReturnObjectObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleIntegrityBroken(Exception ex, WebRequest request){

        exceptionReturnObjectObj.setMessage("Database integrity broken, did you try using an entity that does not exist?");
        exceptionReturnObjectObj.setError(ex.getClass().getCanonicalName());
        exceptionReturnObjectObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        exceptionReturnObjectObj.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionReturnObjectObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidateException(Exception ex, WebRequest request) {

        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }
        exceptionReturnObjectObj.setMessage(sb.toString());
        exceptionReturnObjectObj.setError(ex.getClass().getCanonicalName());
        exceptionReturnObjectObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        exceptionReturnObjectObj.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(exceptionReturnObjectObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllOtherExceptions(Exception ex, WebRequest request) {

        exceptionReturnObjectObj.setMessage(ex.getLocalizedMessage());
        exceptionReturnObjectObj.setError(ex.getClass().getCanonicalName());
        exceptionReturnObjectObj.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        exceptionReturnObjectObj.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(exceptionReturnObjectObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
