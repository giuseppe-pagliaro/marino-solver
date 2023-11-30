package com.giuseppepagliaro.marinosolver.parsers;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;

import com.giuseppepagliaro.marinosolver.exceptions.InvalidTokenSequenceException;
import com.giuseppepagliaro.marinosolver.exceptions.StepAlreadySolvedException;
import com.giuseppepagliaro.marinosolver.exceptions.StepNotYetSolvedException;

/**
 * Represents a sequence of operations of the same type.
 * @author Giuseppe Pagliaro
 * @version 1.0.1
 * @since 1.0.0
 */
public class ProblemStep {
    public ProblemStep(String hash, boolean isParenthesis) {
        this.hash = hash;
        IS_PARENTHESIS = isParenthesis;

        expression = new LinkedList<>();
    }

    public ProblemStep(String hash, boolean isParenthesis, List<String> initTokens) {
        this(hash, isParenthesis);
        memorize(initTokens);
    }

    public ProblemStep(ProblemStep original) {
        this.hash = original.hash;
        this.IS_PARENTHESIS = original.IS_PARENTHESIS;
        this.expression = new LinkedList<>(original.expression);
        this.operator = original.operator;
        this.result = original.result;
        this.timeSolved = original.timeSolved;
    }

    private static final String LEVEL_LABEL = "L";
    private static final String STEP_LABEL = "S";
    private static final String TREE_HASH_REGEX = LEVEL_LABEL + "\\d+" + STEP_LABEL + "\\d+";
    private static final String FIRST_TOKENS_INPUT_VALIDATOR = "^("+ TREE_HASH_REGEX + "|\\d+)(" + ProblemOperator.buildOperatorRegex(ProblemOperator.selectAll()) + "(" + TREE_HASH_REGEX + "|\\d+))+$";
    private static final String TOKENS_INPUT_VALIDATOR = "^(" + ProblemOperator.buildOperatorRegex(ProblemOperator.selectAll()) + "(" + TREE_HASH_REGEX + "|\\d+))+$";

    private String hash;
    private LinkedList<String> expression;
    private String operator;
    private String result;
    private int timeSolved;

    public final boolean IS_PARENTHESIS;

    /**
     * Adds the given tokens to the step.
     * @param newTokens The list of tokens to add.
     * @throws StepAlreadySolvedException if the step that is being modified is already solved.
     */
    public void memorize(List<String> newTokens) throws StepAlreadySolvedException, InvalidTokenSequenceException {
        if (result != null) throw new StepAlreadySolvedException();

        String strNewTokens = "";
        for (String token : newTokens) strNewTokens += token;

        if (operator == null) {
            if (!strNewTokens.matches(FIRST_TOKENS_INPUT_VALIDATOR))
                throw new InvalidTokenSequenceException();
            
            operator = newTokens.get(1);
        } else {
            if (!strNewTokens.matches(TOKENS_INPUT_VALIDATOR))
                throw new InvalidTokenSequenceException();
        }

        // Checks for operator tokens that are different from the stored operator.
        if (strNewTokens.matches(ProblemOperator.buildOperatorRegex(ProblemOperator.selectAll()).replace("\\" + operator, "")))
            throw new InvalidTokenSequenceException();

        // Filters out the operator token instances.
        for (String token : newTokens) {
            if (token.equals(operator)) continue;

            expression.add(token);
        }
    }

    /**
     * Solves the step.
     * @param time The time at which the step was solved, 
     * used to determine when to show the solved step when the expression string 
     * is requested.
     * @param problemTree The tree representation of the problem created by 
     * {@link com.giuseppepagliaro.marinosolver.parsers.Parser}.
     * @throws StepNotYetSolvedException if the step references another step that is not yet solved.
     */
    public void solve(int time, HashMap<String, ProblemStep> problemTree) throws StepNotYetSolvedException {
        timeSolved = time;

        Iterator<String> expressionIt = expression.iterator();
        String token = expressionIt.next();

        result = isATreeHash(token) ? problemTree.get(token).getResult() : token;

        while (expressionIt.hasNext()) {
            token = expressionIt.next();
            String num = isATreeHash(token) ? problemTree.get(token).getResult() : token;

            switch (operator) {
                case "+":
                    result = add(result, num);
                    break;
                
                case "-":
                    result = subtract(result, num);
                    break;
                    
                case "*":
                    result = multiply(result, num);
                    break;
                
                case "/":
                    result = divide(result, num);
                    break;
                    
                case "^":
                    result = power(result, num);
                    break;
            }
        }
    }

    /**
     * Checks if the step is solved at the given time.
     * @param time The time at which to check.
     */
    public boolean isSolved(int time) {
        return timeSolved <= time && result != null;
    }

    public String getResult() throws StepNotYetSolvedException {
        if (result == null) throw new StepNotYetSolvedException();

        return result;
    }
    
    public List<String> getExpression() {
        return new LinkedList<>(expression);
    }

    /**
     * Returns the step in string form.
     */
    public String getExpressionStr() {
        String expr = "";

        Iterator<String> expressionIt = expression.iterator();
        while (expressionIt.hasNext()) {
            expr += expressionIt.next();
            if (expressionIt.hasNext()) expr += operator;
        }

        return expr;
    }

    @Override
    public int hashCode() {
        return hash.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        ProblemStep other = (ProblemStep)obj;

        return this.hash.equals(other.hash);
    }

    @Override
    public String toString() {
        String resultStr = result == null ? "(Not Yet Solved)" : result;
        return hash + ": " + getExpressionStr() + " = " + resultStr;
    }

    /**
     * Builds the string used to create a {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep}.
     * @param currentLevel The current level of the problem tree.
     * @param currentStep The current step of the problem tree.
     * @return The hash string for the new {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep}.
     */
    public static String buildTreeHash(int currentLevel, int currentStep) {
        return LEVEL_LABEL + currentLevel + STEP_LABEL + currentStep;
    }

    /**
     * Builds the string used to create a {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep}.
     * @param currentLevel The current level of the problem tree.
     * @param levelToMaxStepReached A {@link java.util.HashMap} that maps a level to the numbers of steps for that level.
     * @return The hash string for the new {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep}.
     */
    public static String buildTreeHash(int currentLevel, HashMap<Integer, Integer> levelToMaxStepReached) {
        return buildTreeHash(currentLevel, levelToMaxStepReached.get(currentLevel));
    }

    /**
     * Determines whether or not the given token is a tree hash.
     * @param token The token to evaluate.
     */
    public static boolean isATreeHash(String token) {
        return token.matches("^(" + TREE_HASH_REGEX + ")$");
    }

    /**
     * Calculates the sum of two values provided in string form.
     * @param n1 addend one;
     * @param n2 addend two.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String add(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) + Double.parseDouble(n2));
    }

    /**
     * Calculates the subtraction of two values provided in string form.
     * @param n1 the minuend;
     * @param n2 the subtractor.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String subtract(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) - Double.parseDouble(n2));
    }

    /**
     * Calculates the multiplication of two values provided in string form.
     * @param n1 multiplier one;
     * @param n2 multiplier two.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String multiply(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) * Double.parseDouble(n2));
    }

    /**
     * Calculates the division of two values provided in string form.
     * @param n1 the numerator;
     * @param n2 the denominator.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String divide(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) / Double.parseDouble(n2));
    }

    /**
     * Calculates the power of two values provided in string form.
     * @param n1 the base;
     * @param n2 the exponent.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String power(String n1, String n2) throws NumberFormatException {
        return "" + (Math.pow(Double.parseDouble(n1), Double.parseDouble(n2)));
    }
}
