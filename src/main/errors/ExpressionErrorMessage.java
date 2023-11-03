package src.main.errors;

public enum ExpressionErrorMessage {
    PARENTHESIS_NOT_CLOSED("Parenthesis opened at %1$s but never closed!"),
    PARENTHESIS_NEVER_OPENED("Parenthesis closed at %1$s but never opened!"),
    DIVIDING_BY_ZERO("Attempt to divide by zero at %1$s!"),
    CONSECUTIVE_OPERATION_SIGN("Two consecutive operation signs were found at %1$s!"),
    NON_PARSABLE_NUMBER_TOKEN("One of the tokens near the operator at %1$s can't be a number!");

    private ExpressionErrorMessage(String partialErrorMessage) {
        this.partialErrorMessage = partialErrorMessage;
    }

    private final String partialErrorMessage;

    public void printErrorMessage(int index) {
        throw new IncorrectExpressionSyntaxException(String.format(partialErrorMessage, ""+index));
    }
}
