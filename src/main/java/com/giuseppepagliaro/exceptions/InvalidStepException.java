package com.giuseppepagliaro.exceptions;

public class InvalidStepException extends RuntimeException{
    public InvalidStepException() {
        super("An invalid step was fed to the Step Calculator.");
    }
}
