package com.example.pizza.exception.exceptions;

public class EmptyPizzaListException extends RuntimeException{
    public EmptyPizzaListException(String message) {
        super(message);
    }
}
