package com.giuseppepagliaro.exceptions;

/**
 * a defacto factory for {@link com.giuseppepagliaro.exceptions.IncorrectProblemSyntaxException}
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ProblemErrorMessage {
    PARENTHESIS_NOT_CLOSED("Parenthesis opened at %1$s but never closed!"),
    PARENTHESIS_NEVER_OPENED("Parenthesis closed at %1$s but never opened!"),
    DIVIDING_BY_ZERO("Attempt to divide by zero at %1$s!"),
    CONSECUTIVE_OPERATION_SIGN("Two consecutive operation signs were found at %1$s!"),
    INCOMPLETE_OPERATION("Operator not followed by a number at %1$s!"),
    NON_PARSABLE_NUMBER_TOKEN("The token at %1$s can't be a number!"),
    EMPTY_PROBLEM("An empty problem was provided at %1$s!");

    private ProblemErrorMessage(String partialErrorMessage) {
        this.partialErrorMessage = partialErrorMessage;
    }

    private final String partialErrorMessage;

    public void print(int index) {
        throw new IncorrectProblemSyntaxException(String.format(partialErrorMessage, ""+index));
    }
}
