package com.giuseppepagliaro.errors;

public class NoMoreStepsException extends RuntimeException {
    public NoMoreStepsException() {
        super("Trying to solve more steps on an already solved expression.");
    }
}
