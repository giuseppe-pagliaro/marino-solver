package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Thrown when the solveStep method in {@link com.giuseppepagliaro.marinosolver.solvers.Solver} 
 * is called on a fully solved problem.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class NoMoreStepsException extends RuntimeException {
    public NoMoreStepsException() {
        super("Trying to solve more steps on an already solved problem.");
    }
}
