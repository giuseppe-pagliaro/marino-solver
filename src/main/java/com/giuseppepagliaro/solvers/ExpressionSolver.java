package com.giuseppepagliaro.solvers;

import static com.giuseppepagliaro.commons.StringBuilders.buildTreeHash;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.parsers.Parser;

/**
 * The implementation of {@link com.giuseppepagliaro.solvers.Solver} for expressions.
 */
public class ExpressionSolver extends Solver{
    public ExpressionSolver(Parser parser, boolean saveHistory) {
        super(parser, saveHistory);

        currentLevel = parser.getMaxLevelReached();
        currentStep = 0;
        solutionPrinted = false;
    }

    private int currentLevel;
    private int currentStep;
    private boolean solutionPrinted;

    @Override
    public void solveStep() throws NoMoreStepsException {
        if (!hasMoreSteps() && solutionPrinted && problemHistory != null)
            throw new NoMoreStepsException();
        
        if (!hasMoreSteps()) {
            latestStep = "Solution: " + StepCalculator.toString((String[])problemTree.get("L0").toArray());
            solutionPrinted = true;
            return;
        }

        int maxStepReached = levelToMaxStep.get(currentLevel);

        if (maxStepReached == -1) {
            levelToMaxStep.remove(currentLevel);
            currentLevel--;
            maxStepReached = levelToMaxStep.get(currentLevel);
            currentStep = 0;
        }

        String[] step = (String[])problemTree.get(buildTreeHash(maxStepReached, levelToMaxStep)).toArray();
        problemTree.remove(buildTreeHash(maxStepReached, levelToMaxStep));

        String stepRes = StepCalculator.calculateStep(step);
        int levelToChange = currentLevel == 0 ? 0 : currentLevel - 1;

        int stepRefInd = 0;
        while (problemTree.get(buildTreeHash(levelToChange, currentStep)).get(stepRefInd) != buildTreeHash(maxStepReached, levelToMaxStep)) {
            stepRefInd++;
        }

        problemTree.get(buildTreeHash(levelToChange, currentStep)).set(stepRefInd, stepRes);

        if (problemHistory == null) latestStep = "Solved: " + StepCalculator.toString(step);
        else problemHistory.add(getProblem());
    }
}
