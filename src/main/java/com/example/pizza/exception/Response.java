package com.example.pizza.exception;

/**
 * The Response class represents a simple response object with a message.
 * It is used to provide custom error messages in ResponseEntity objects.
 */
public class Response {
    /**
     * The message associated with the response.
     */
    private String message;

    /**
            * Constructs a new Response object with the given message.
            *
            * @param message The message to be associated with the response.
     */
    public Response(String message) {
        this.message = message;
    }

    /**
     * Get the message associated with the response.
     *
     * @return The message associated with the response.
     */
    public String getMessage() {
        return message;
    }
}
