package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Thrown when the memorize method is called on a solved 
 * {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepAlreadySolvedException extends RuntimeException {
    public StepAlreadySolvedException() {
        super("This step is already solved and cannot be modified.");
    }
}
