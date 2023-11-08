package com.giuseppepagliaro.exceptions;

/**
 * Thrown when the memorize method is called on a solved 
 * {@link com.giuseppepagliaro.commons.ProblemStep}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class StepAlreadySolvedException extends RuntimeException{
    public StepAlreadySolvedException() {
        super("An invalid step was fed to the Step Calculator.");
    }
}
