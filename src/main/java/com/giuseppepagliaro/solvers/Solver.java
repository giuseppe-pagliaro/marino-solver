package com.giuseppepagliaro.solvers;

import java.util.LinkedList;

import com.giuseppepagliaro.commons.ProblemStep;
import com.giuseppepagliaro.exceptions.StepNotYetReachedException;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.exceptions.StepNotYetSolvedException;
import com.giuseppepagliaro.parsers.ObjectOrientedParser;

/**
 * Main contract for problem solvers of the Marino Solver library.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Solver {
    public Solver(ObjectOrientedParser parser, boolean saveHistory) {
        PARSER = parser;
        maxTime = 0;
    }
    
    protected final ObjectOrientedParser PARSER;
    private int maxTime;

    /**
     * Gets the step at the given time (if reached).
     * @return A string containing the step.
     * @throws
     */
    public String getStep(int time) throws StepNotYetReachedException {
        if (time > maxTime) throw new StepNotYetReachedException();

        return getProblem(PARSER.getProblemTree().get("L0"), time);
    }
    
    /**
     * Gets the original state of the problem.
     * @return A string containing the problem.
     */
    public String getBase() {
        return getStep(0);
    }

    public String getResult() throws StepNotYetReachedException {
        if (hasMoreSteps()) throw new StepNotYetReachedException();

        return getStep(maxTime);
    }

    /**
     * Gets all the steps executed so far.
     * @return A {@link java.util.LinkedList} containing the steps from oldest to newest:
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
     * @return "true" if the problem has more steps, "false" if it doesn't.
     */
    public boolean hasMoreSteps() {
        try {
            PARSER.getProblemTree().get("l0").getResult();
            return true;
        } catch (StepNotYetSolvedException e) {
            return false;
        }
    }

    /**
     * Solves the next due step of the problem (should call incTime).
     * @return A string containing the problem up to that point.
     * @throws NoMoreStepsException if the problem is already fully solved.
     */
    public abstract void solveStep() throws NoMoreStepsException;

    /**
     * Turns the problem tree into a string using dfs.
     * @return A string containing the problem.
     */
    
    protected void incTime() {
        maxTime++;
    }

    protected int getMaxTime() {
        return maxTime;
    }

    /**
     * Turns a step sub-tree into a string representing the step at a given time.
     * @param step The step to convert.
     * @param time The time at which we need the step.
     * @return A string representing the step.
     */
    protected abstract String getProblem(ProblemStep step, int time);
}
