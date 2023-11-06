package com.giuseppepagliaro.exceptions;

/**
 * A defacto factory for {@link com.giuseppepagliaro.exceptions.IncorrectProblemSyntaxException}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ProblemErrorMessage {
    PARENTHESIS_NOT_CLOSED("Parenthesis should be closed at %1$s!"),
    PARENTHESIS_NEVER_OPENED("Parenthesis closed at %1$s but never opened!"),
    INCOMPLETE_OPERATION("Operator not followed by a number at %1$s!"),
    NON_PARSABLE_NUMBER_TOKEN("The token at %1$s can't be a number!"),
    EMPTY_PROBLEM("An empty problem was provided at %1$s!");

    private ProblemErrorMessage(String partialErrorMessage) {
        this.partialErrorMessage = partialErrorMessage;
    }

    private final String partialErrorMessage;

    public void print(int index) throws IncorrectProblemSyntaxException {
        throw new IncorrectProblemSyntaxException(String.format(partialErrorMessage, ""+index));
    }
}
