package com.giuseppepagliaro.exceptions;

/**
 * Thrown when attempting to get history details from a solver initialized with 
 * the saveHistory option to false.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepNotYetReachedException extends RuntimeException {
    public StepNotYetReachedException() {
        super("The time given has not yet been reached.");
    }
}
