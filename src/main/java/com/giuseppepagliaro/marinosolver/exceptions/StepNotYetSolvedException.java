package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Thrown when trying to access the result of a 
 * {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep} 
 * that is not solved yet.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepNotYetSolvedException extends RuntimeException {
    public StepNotYetSolvedException() {
        super("Trying to the access result info of a step that is not yet solved.");
    }
}
