package com.giuseppepagliaro.solvers;

import com.giuseppepagliaro.commons.ProblemStep;
import com.giuseppepagliaro.commons.StringBuilders;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.exceptions.StepNotYetSolvedException;
import com.giuseppepagliaro.parsers.Parser;

/**
 * The implementation of {@link com.giuseppepagliaro.solvers.Solver} for expressions.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExpressionSolver extends Solver {
    public ExpressionSolver(Parser parser) {
        super(parser);

        currentLevel = PARSER.getMaxLevelReached();
        currentStep = -1;
    }

    private int currentLevel;
    private int currentStep;

    @Override
    public void solveStep() throws NoMoreStepsException {
        if (!hasMoreSteps()) throw new NoMoreStepsException();

        incTime();

        if (currentStep + 1 > PARSER.getLevelToMaxStepReached().get(currentLevel)) {
            currentStep = -1;
            currentLevel--;
        } else {
            currentStep++;
        }
        
        try {
            PARSER.getProblemTree().get(StringBuilders.buildTreeHash(currentLevel, currentStep)).solve(getMaxTime(), PARSER.getProblemTree());
        } catch (StepNotYetSolvedException e) { }
    }

    @Override
    protected String getProblem(ProblemStep step, int time) {
        // Using DFS
        
        try {
            if (step.getTimeSolved() <= time) return step.getResult();
        } catch (StepNotYetSolvedException e) { }

        String expressionStr = "";

        for (String token : step.getExpression()) {
            if (StringBuilders.isATreeHash(token)) {
                String tokenValue = getProblem(PARSER.getProblemTree().get(token), time);

                if (step.IS_PARENTHESIS) {
                    expressionStr += "(" + tokenValue + ")";
                } else {
                    expressionStr += tokenValue;
                }
            } else {
                expressionStr += token;
            }
        }

        return expressionStr;
    }
}
