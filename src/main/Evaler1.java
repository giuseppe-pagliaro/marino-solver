package src.main;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import src.main.errors.ExpressionErrorMessage;

public class Evaler1 {
    private Evaler1() {}

    private static final String EXPRESSION_DELIMITERS = " +-*/()";
    
    public static ArrayList<String> tokenizeExpression(String expression) {
        expression = expression.trim();
        StringTokenizer exprTokenizer = new StringTokenizer(expression, EXPRESSION_DELIMITERS, true);
        ArrayList<String> tockenizedExpression = new ArrayList<>();

        while (exprTokenizer.hasMoreTokens()) {
            tockenizedExpression.add(exprTokenizer.nextToken());
        }

        return tockenizedExpression;
    }

    public static String evaluateExpression(ArrayList<String> tokenizedExpression) { 
        Parenthesis parenthesis = Parenthesis.findParenthesis(tokenizedExpression);

        if (parenthesis == null) {
            return evaluatePlusOrMinus(tokenizedExpression);
        }

        ArrayList<String> subExpr = castList(tokenizedExpression.subList(parenthesis.getStart() + 1, parenthesis.getEnd() - 1));
        tokenizedExpression.set(parenthesis.getStart(), evaluateExpression(subExpr));
        for (int i = parenthesis.getStart() + 1; i < parenthesis.getEnd() + 1; i++)
            tokenizedExpression.remove(i);
        
        System.out.println("Step: " + getPartialExpression(tokenizedExpression));
        return evaluateExpression(tokenizedExpression);
    }

    private static String getPartialExpression(ArrayList<String> tokenizedExpression) {
        String expression = "";

        for (String token : tokenizedExpression) {
            expression += token;
        }

        return expression;
    }
    
    private static String evaluatePlusOrMinus(ArrayList<String> tokenizedOperations) {
        if (tokenizedOperations.size() == 1)
            return tokenizedOperations.get(0);
        
        int plusInd = tokenizedOperations.indexOf("+");
        int minusInd = tokenizedOperations.indexOf("-");

        if (plusInd == -1 && minusInd == -1) {
            return evaluateTimesOrOver(tokenizedOperations);
        }
        
        int nextOperationInd = 0;
        if (plusInd == -1) nextOperationInd = minusInd;
        else if (minusInd == -1) nextOperationInd = plusInd;
        else nextOperationInd = plusInd < minusInd ? plusInd : minusInd;

        return evaluateOperation("+-", tokenizedOperations, nextOperationInd);
    }

    private static String evaluateTimesOrOver(ArrayList<String> tokenizedOperations) {
        if (tokenizedOperations.size() == 1)
            return tokenizedOperations.get(0);
        
        int timesInd = tokenizedOperations.indexOf("*");
        int overInd = tokenizedOperations.indexOf("/");

        int nextOperationInd = 0;
        if (timesInd == -1) nextOperationInd = overInd;
        else if (overInd == -1) nextOperationInd = timesInd;
        else nextOperationInd = timesInd < overInd ? timesInd : overInd;

        return evaluateOperation("*/", tokenizedOperations, nextOperationInd);
    }
    
    private static String evaluateOperation(String opType, ArrayList<String> tokenizedOperations, int nextOperationInd) {
        ArrayList<String> leftOperation = castList(tokenizedOperations.subList(0, nextOperationInd));
        ArrayList<String> rightOperation = castList(tokenizedOperations.subList(nextOperationInd + 1, tokenizedOperations.size()));

        if (leftOperation.size() == 0 || rightOperation.size() == 0)
            ExpressionErrorMessage.CONSECUTIVE_OPERATION_SIGN.printErrorMessage(nextOperationInd);
        
        if (leftOperation.size() == 1 && rightOperation.size() == 1)
            return calculate(leftOperation.get(0), rightOperation.get(0), tokenizedOperations.get(nextOperationInd), nextOperationInd);
        
        if (leftOperation.size() > 1) {
            tokenizedOperations.set(0, opType.equals("+-") ? evaluatePlusOrMinus(leftOperation) : evaluateTimesOrOver(leftOperation));
            for (int i = 1; i < nextOperationInd; i++) {
                tokenizedOperations.remove(i);
            }
        }
        if (rightOperation.size() > 1) {
            tokenizedOperations.set(nextOperationInd + 1, opType.equals("+-") ? evaluatePlusOrMinus(rightOperation) : evaluateTimesOrOver(rightOperation));
            for (int i = nextOperationInd + 1; i < tokenizedOperations.size(); i++) {
                tokenizedOperations.remove(i);
            }
        }
        
        System.out.println("Step: " + getPartialExpression(tokenizedOperations));
        return opType.equals("+-") ? evaluatePlusOrMinus(tokenizedOperations) : evaluateTimesOrOver(tokenizedOperations);
    }

    private static String calculate(String left, String right, String operator, int operatorInd) {
        double parsedLeft;
        double parsedRight;

        try {
            parsedLeft = Double.parseDouble(left);
            parsedRight = Double.parseDouble(right);
        } catch (NumberFormatException e) {
            ExpressionErrorMessage.NON_PARSABLE_NUMBER_TOKEN.printErrorMessage(operatorInd);
            return "";
        }

        switch (operator) {
            case "+":
                return "" + (parsedLeft + parsedRight);
            
            case "-":
                return "" + (parsedLeft - parsedRight);
            
            case "*":
                return "" + (parsedLeft * parsedRight);
            
            case "/":
                if (parsedRight == 0) ExpressionErrorMessage.DIVIDING_BY_ZERO.printErrorMessage(operatorInd);
                return "" + (parsedLeft / parsedRight);
        }

        return "";
    }

    private static ArrayList<String> castList(List<String> l) {
        ArrayList<String> al = new ArrayList<>(l.size());

        for(String str: l)
            al.add(str);

        return al;
    }
}
