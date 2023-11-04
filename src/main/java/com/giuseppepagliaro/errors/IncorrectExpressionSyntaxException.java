package com.giuseppepagliaro.errors;

public class IncorrectExpressionSyntaxException extends RuntimeException {
    IncorrectExpressionSyntaxException(String errorMgs) {
        super(errorMgs);
    }
}
