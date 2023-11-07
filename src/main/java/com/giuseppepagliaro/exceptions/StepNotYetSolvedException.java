package com.giuseppepagliaro.exceptions;

/**
 * Thrown when trying to access the result of a {@link com.giuseppepagliaro.commons.ProblemStep} 
 * that is not solved yet.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepNotYetSolvedException extends Exception {
    public StepNotYetSolvedException() {
        super("Trying to access result info of a step that's not yet solved.");
    }
}
