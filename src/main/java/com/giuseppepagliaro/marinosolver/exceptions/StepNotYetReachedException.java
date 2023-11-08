package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Thrown when trying to access a step at a time interval greater than maxTime in a 
 * {@link com.giuseppepagliaro.marinosolver.solvers.Solver} child.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepNotYetReachedException extends RuntimeException {
    public StepNotYetReachedException() {
        super("The time given has not yet been reached.");
    }
}
