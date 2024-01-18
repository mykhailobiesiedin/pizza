package com.example.pizza.exception;

import com.example.pizza.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The Advice class is a controller advice that handles exceptions thrown by the application.
 * It uses the @ExceptionHandler annotation to define methods for handling specific exceptions
 * and returning appropriate ResponseEntity objects with error responses.
 */
@ControllerAdvice
public class Advice {

    /**
     * Handles CafeNotFoundException and returns a ResponseEntity with a NOT_FOUND status and a custom error response.
     *
     * @param e The exception thrown when a cafe is not found.
     * @return ResponseEntity with a custom error response and NOT_FOUND status.
     */
    @ExceptionHandler(CafeNotFoundException.class)
    public ResponseEntity<Response> handleException(CafeNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles EmptyCafeListException and returns a ResponseEntity with a NO_CONTENT status and a custom error response.
     *
     * @param e The exception thrown when the cafe list is empty.
     * @return ResponseEntity with a custom error response and NO_CONTENT status.
     */
    @ExceptionHandler(EmptyCafeListException.class)
    public ResponseEntity<Response> handleException(EmptyCafeListException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    /**
     * Handles EmptyPizzaListException and returns a ResponseEntity with a NO_CONTENT status and a custom error response.
     *
     * @param e The exception thrown when the pizza list is empty.
     * @return ResponseEntity with a custom error response and NO_CONTENT status.
     */
    @ExceptionHandler(EmptyPizzaListException.class)
    public ResponseEntity<Response> handleException(EmptyPizzaListException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    /**
     * Handles IdNotFoundException and returns a ResponseEntity with a NOT_FOUND status and a custom error response.
     *
     * @param e The exception thrown when an ID is not found.
     * @return ResponseEntity with a custom error response and NOT_FOUND status.
     */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<Response> handleException(IdNotFoundException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles PizzaNotFoundException and returns a ResponseEntity with a NOT_FOUND status and a custom error response.
     *
     * @param e The exception thrown when a pizza is not found.
     * @return ResponseEntity with a custom error response and NOT_FOUND status.
     */
    @ExceptionHandler(PizzaNotFoundException.class)
    public ResponseEntity<Response> handleException(PizzaNotFoundException e){
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
