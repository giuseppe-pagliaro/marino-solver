package com.giuseppepagliaro.exceptions;

public class StepNotYetSolvedException extends Exception {
    public StepNotYetSolvedException() {
        super("Trying to access result info of a step that's not yet solved.");
    }
}
