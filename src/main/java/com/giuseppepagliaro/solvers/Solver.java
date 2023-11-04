package com.giuseppepagliaro.solvers;

import java.util.LinkedList;

import com.giuseppepagliaro.exceptions.HistoryWasNotTrackedException;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;

/**
 * main contract for problem solvers of the Marino Solver library
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Solver {
    /**
     * gets the latest step of the problem or the solution if it's solved
     * @return a String containing the step
     */
    String getLatestStep();
    
    /**
     * gets the original state of the problem
     * @return a String containing the problem
     */
    String getBase() throws HistoryWasNotTrackedException;

    /**
     * gets all the steps executed so far
     * @return a {@link java.util.LinkedList} containing the steps from oldest to newest
     */
    LinkedList<String> getHistory() throws HistoryWasNotTrackedException;

    /**
     * checks if the problem is solved or not
     * @return "true" if the problem has more steps, "false" if it doesn't
     */
    boolean hasMoreSteps();

    /**
     * solves the next due step of the problem
     * @return a string containing the problem up to that point
     * @throws NoMoreStepsException if the problem is already fully solved
     */
    String solveStep() throws NoMoreStepsException;
}
