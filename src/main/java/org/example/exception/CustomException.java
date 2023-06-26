package org.example.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class CustomException extends RuntimeException{
    public CustomException(String message) {


        super(message);
    }
}

