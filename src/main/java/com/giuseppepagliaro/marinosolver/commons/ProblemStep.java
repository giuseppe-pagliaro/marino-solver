package com.giuseppepagliaro.marinosolver.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.giuseppepagliaro.marinosolver.exceptions.StepAlreadySolvedException;
import com.giuseppepagliaro.marinosolver.exceptions.StepNotYetSolvedException;

/**
 * Represents a leaf of the problem tree returned by 
 * a {@link com.giuseppepagliaro.marinosolver.parsers.Parser}.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class ProblemStep {
    public ProblemStep(String hash, boolean isParenthesis) {
        this.hash = hash;
        IS_PARENTHESIS = isParenthesis;

        expression = new ArrayList<>();
    }

    public ProblemStep(ProblemStep original) {
        this.hash = original.hash;
        this.IS_PARENTHESIS = original.IS_PARENTHESIS;
        this.expression = new ArrayList<>(original.expression);
        this.result = original.result;
        this.timeSolved = original.timeSolved;
    }

    private String hash;
    private ArrayList<String> expression;
    private String result;
    private int timeSolved;

    public final boolean IS_PARENTHESIS;

    /**
     * Adds the given tokens to the step.
     * @param newTokens The tokens to add.
     * @throws StepAlreadySolvedException if the step that is being modified is already solved.
     */
    public void memorize(List<String> newTokens) throws StepAlreadySolvedException {
        if (result != null) throw new StepAlreadySolvedException();

        expression.addAll(new ArrayList<>(newTokens));
    }

    /**
     * Solves the step.
     * @param time The time at which the step was solved, 
     * used to determine when to show the solved step when the expression string 
     * is requested.
     * @param problemTree The tree representation of the problem created by 
     * {@link com.giuseppepagliaro.marinosolver.parsers.Parser}.
     * @throws StepNotYetSolvedException if the step references another step that is not 
     * yet solved.
     */
    public void solve(int time, HashMap<String, ProblemStep> problemTree) throws StepNotYetSolvedException {
        timeSolved = time;

        String n1 = StringBuilders.isATreeHash(expression.get(0)) ? problemTree.get(expression.get(0)).getResult() : expression.get(0);
        String n2 = StringBuilders.isATreeHash(expression.get(2)) ? problemTree.get(expression.get(2)).getResult() : expression.get(2);
        switch (expression.get(1)) {
            case "+":
                result = StepCalculator.add(n1, n2);
                break;
            
            case "-":
                result = StepCalculator.subtract(n1, n2);
                break;
                
            case "*":
                result = StepCalculator.multiply(n1, n2);
                break;
            
            case "/":
                result = StepCalculator.divide(n1, n2);
                break;
                
            case "^":
                result = StepCalculator.power(n1, n2);
                break;
        }

        String op = null;
        for (int i = 3; i < expression.size(); i++) {
            if (op == null) {
                op = expression.get(i);
                continue;
            }

            String n = StringBuilders.isATreeHash(expression.get(i)) ? problemTree.get(expression.get(i)).getResult() : expression.get(i);
            switch (op) {
                case "+":
                    result = StepCalculator.add(result, n);
                    break;
                
                case "-":
                    result = StepCalculator.subtract(result, n);
                    break;
                    
                case "*":
                    result = StepCalculator.multiply(result, n);
                    break;
                
                case "/":
                    result = StepCalculator.divide(result, n);
                    break;
                    
                case "^":
                    result = StepCalculator.power(result, n);
                    break;
            }

            op = null;
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
    
    public ArrayList<String> getExpression() {
        return new ArrayList<>(expression);
    }

    /**
     * Returns the step in string form.
     */
    public String getExpressionStr() {
        String stepStr = "";

        for (String token : expression) {
            stepStr += token;
        }

        return stepStr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((hash == null) ? 0 : hash.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProblemStep other = (ProblemStep) obj;
        if (hash == null) {
            if (other.hash != null)
                return false;
        } else if (!hash.equals(other.hash))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String resultStr = result == null ? "(Not Yet Solved)" : result;
        return hash + ": " + getExpressionStr() + " = " + resultStr;
    }
}
