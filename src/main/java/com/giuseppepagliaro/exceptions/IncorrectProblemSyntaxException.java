package com.giuseppepagliaro.exceptions;

/**
 * Trown when an incorrect problem is fed to the solver. 
 * It can't be instantiated directly but only through {@link com.giuseppepagliaro.exceptions.ProblemErrorMessage}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class IncorrectProblemSyntaxException extends RuntimeException {
    IncorrectProblemSyntaxException(String errorMgs) {
        super(errorMgs);
    }
}
