package com.giuseppepagliaro.exceptions;

/**
 * Thrown when an invalid step is fed to a 
 * {@link com.giuseppepagliaro.commons.StepCalculator} method.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvalidStepException extends RuntimeException{
    public InvalidStepException() {
        super("An invalid step was fed to the Step Calculator.");
    }
}
