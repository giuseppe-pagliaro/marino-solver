package src.main.errors;

public class IncorrectExpressionSyntaxException extends RuntimeException {
    IncorrectExpressionSyntaxException(String errorMgs) {
        super(errorMgs);
    }
}
