package com.giuseppepagliaro;

import java.util.LinkedList;

import com.giuseppepagliaro.errors.NoMoreStepsException;

public interface Evaler {
    /**
     * gets the latest step of the problem
     * @return a String containing the step
     */
    String getSolution();
    
    /**
     * gets the original state of the problem
     * @return a String containing the problem
     */
    String getBase();

    /**
     * gets all the steps executed so far
     * @return a {@link java.util.LinkedList} containing the steps from oldest to newest
     */
    LinkedList<String> getHistory();

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
