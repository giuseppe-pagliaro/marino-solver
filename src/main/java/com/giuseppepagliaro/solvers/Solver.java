package com.giuseppepagliaro.solvers;

import java.util.LinkedList;

import com.giuseppepagliaro.commons.ProblemStep;
import com.giuseppepagliaro.commons.StringBuilders;
import com.giuseppepagliaro.exceptions.StepNotYetReachedException;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.exceptions.StepNotYetSolvedException;
import com.giuseppepagliaro.parsers.Parser;

/**
 * Main contract for problem solvers of the Marino Solver library.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Solver {
    public Solver(Parser parser) {
        PARSER = new Parser(parser);
        maxTime = 0;
    }
    
    protected final Parser PARSER;
    private int maxTime;

    /**
     * Gets the step at the given time (if reached).
     * @param time The time corresponding to the desired step.
     * @return A string containing the step.
     * @throws StepNotYetReachedException if time is greater than maxTime.
     */
    public String getStep(int time) throws StepNotYetReachedException {
        if (time > maxTime) throw new StepNotYetReachedException();

        return getProblem(PARSER.getProblemTree().get(StringBuilders.buildTreeHash(0, 0)), time);
    }
    
    /**
     * Gets the original state of the problem.
     * @return A string containing the problem.
     */
    public String getBase() {
        return getStep(0);
    }

    /**
     * Gets the result of the problem.
     * @return A string containing the problem.
     * @throws StepNotYetReachedException if the problem is not solved yet.
     */
    public String getResult() throws StepNotYetReachedException {
        if (hasMoreSteps()) throw new StepNotYetReachedException();

        return getStep(maxTime);
    }

    /**
     * Gets all the steps executed so far (warning: it has a high time complexity).
     * @return A {@link java.util.LinkedList} containing the steps from oldest to newest.
     */
    public LinkedList<String> getHistory() throws StepNotYetReachedException {
        LinkedList<String> history = new LinkedList<>();

        for (int i = 0; i < maxTime; i++) {
            history.add(getStep(i));
        }

        return history;
    }

    /**
     * Checks if the problem is solved or not.
     */
    public boolean hasMoreSteps() {
        try {
            PARSER.getProblemTree().get(StringBuilders.buildTreeHash(0, 0)).getResult();
            return false;
        } catch (StepNotYetSolvedException e) {
            return true;
        }
    }

    /**
     * Solves the next due step of the problem (should call incTime).
     * @return A string containing the problem up to that point.
     * @throws NoMoreStepsException if the problem is already fully solved.
     */
    public abstract void solveStep() throws NoMoreStepsException;

    protected void incTime() {
        maxTime++;
    }

    protected int getMaxTime() {
        return maxTime;
    }

    /**
     * Turns a step sub-tree into a string representing the step at a given time.
     * @param step The step to convert.
     * @param time The time at which the step is needed.
     * @return A string representing the step.
     */
    protected abstract String getProblem(ProblemStep step, int time);
}
