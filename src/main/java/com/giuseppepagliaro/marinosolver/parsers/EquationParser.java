package com.giuseppepagliaro.marinosolver.parsers;

import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;

public class EquationParser {
    public EquationParser(String equation) throws IncorrectProblemSyntaxException {
        int equalsCount = equation.length() - equation.replace("=", "").length();
        if (equalsCount != 1) throw new IllegalArgumentException("The problem must contain one \"=\" sign!");

        int equalsInd = equation.indexOf("=");
        leftExpression = new Parser(equation.substring(0, equalsInd));
        rightExpression = new Parser(equation.substring(equalsInd + 1, equation.length()));
    }

    public EquationParser(EquationParser original) {
        this.leftExpression = new Parser(original.leftExpression);
        this.rightExpression = new Parser(original.rightExpression);
    }

    private Parser leftExpression;
    private Parser rightExpression;

    public Parser getLeftExpression() {
        return leftExpression;
    }

    public Parser getRightExpression() {
        return rightExpression;
    }
}
