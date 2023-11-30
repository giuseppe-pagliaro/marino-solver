package com.giuseppepagliaro.marinosolver.solvers;

import com.giuseppepagliaro.marinosolver.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.marinosolver.parsers.Parser;
import com.giuseppepagliaro.marinosolver.parsers.ProblemStep;

/**
 * The implementation of {@link com.giuseppepagliaro.marinosolver.solvers.Solver} 
 * for expressions.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class ExpressionSolver extends Solver {
    public ExpressionSolver(Parser parser) {
        super(parser);

        currentLevel = PARSER.getMaxLevelReached();
        currentStep = 0;
    }

    private int currentLevel;
    private int currentStep;

    @Override
    public void solveStep() throws NoMoreStepsException {
        if (!hasMoreSteps()) throw new NoMoreStepsException();

        incTime();

        if (currentStep > PARSER.getLevelToMaxStepReached().get(currentLevel)) {
            currentStep = 0;
            currentLevel--;
        }

        PARSER.getProblemTree().get(ProblemStep.buildTreeHash(currentLevel, currentStep)).solve(getMaxTime(), PARSER.getProblemTree());
        currentStep++;
    }

    @Override
    protected String getProblem(ProblemStep step, int time) {
        // Using DFS
        
        if (step.isSolved(time)) return step.getResult();

        String expressionStr = "";

        for (String token : step.getExpression()) {
            if (ProblemStep.isATreeHash(token)) {
                String tokenValue = getProblem(PARSER.getProblemTree().get(token), time);
                ProblemStep referencedStep = PARSER.getProblemTree().get(token);

                if ((referencedStep.IS_PARENTHESIS && !referencedStep.isSolved(time)) ||
                    (referencedStep.isSolved(time) && Double.parseDouble(referencedStep.getResult()) < 0)) {
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
