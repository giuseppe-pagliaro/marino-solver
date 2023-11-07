package com.giuseppepagliaro.commons;

import java.util.ArrayList;
import java.util.List;

import com.giuseppepagliaro.exceptions.StepNotYetSolvedException;

/**
 * Represents a branch of the problem tree returned by 
 * a {@link com.giuseppepagliaro.parsers.Parser}.
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

    private String hash;
    private ArrayList<String> expression;
    private String result;
    private int timeSolved;

    public final boolean IS_PARENTHESIS;

    /**
     * Adds the given tokens to the step.
     * @param newTokens The tokens to add.
     */
    public void memorize(List<String> newTokens) {
        expression.addAll(new ArrayList<>(newTokens));
    }

    /**
     * Solves the step.
     * @param time The time at which the step was solved, 
     * used to determine when to show the solved step when the expression string 
     * is requested.
     */
    public void solve(int time) {
        result = StepCalculator.calculateStep((String[])expression.toArray());
        timeSolved = time;
    }

    public String getResult() throws StepNotYetSolvedException {
        if (result == null) throw new StepNotYetSolvedException();

        return result;
    }

    public int getTimeSolved() throws StepNotYetSolvedException {
        if (result == null) throw new StepNotYetSolvedException();

        return timeSolved;
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
        return hash + ": " + getExpressionStr() + " = " + result == null ? "(Not Yet Calculated)" : result;
    }
}
