package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Thrown when attempting to memorize an invalid sequence of tokens in a 
 * {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep} instance.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.1
 */
public class InvalidTokenSequenceException extends RuntimeException {
    public InvalidTokenSequenceException() {
        super("Attempting to memorize an invalid sequence of tokens in a step.");
    }
}
