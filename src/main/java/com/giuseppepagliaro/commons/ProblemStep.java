package com.giuseppepagliaro.commons;

import java.util.ArrayList;
import java.util.List;

import com.giuseppepagliaro.exceptions.StepNotYetSolvedException;

/**
 * Represents a branch of the problem tree returned by 
 * a {@link com.giuseppepagliaro.parsers.Parser}.
 * @author Giuseppe Pagliaro
 * @version 1.1.0
 * @since 1.1.0
 */
public class ProblemStep {
    public ProblemStep(String hash) {
        this.hash = hash;
        expression = new ArrayList<>();
    }

    private String hash;
    private ArrayList<String> expression;
    private String result;
    private int timeSolved;

    public void memorize(List<String> newTokens) {
        expression.addAll(new ArrayList<>(newTokens));
    }

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
        return hash + ": " + getExpression() + " = " + result == null ? "(Not Yet Calculated)" : result;
    }
}
