package com.giuseppepagliaro.marinosolver.exceptions;

/**
 * Trown when an incorrect problem is fed to the solver. 
 * It can't be instantiated directly but only through 
 * {@link com.giuseppepagliaro.marinosolver.exceptions.ProblemErrorMessage}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class IncorrectProblemSyntaxException extends Exception {
    IncorrectProblemSyntaxException(String errorMgs) {
        super(errorMgs);
    }
}
