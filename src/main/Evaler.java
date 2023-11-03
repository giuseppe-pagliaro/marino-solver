package src.main;

import java.util.LinkedList;

import src.main.errors.NoMoreStepsException;

public interface Evaler {
    String getExpression();
    
    String getBaseExpression();

    /**
     * gets all the steps executed so far
     * @return a {@link java.util.LinkedList} containing the steps from oldest to newest
     */
    LinkedList<String> getExpressionHistory();

    /**
     * checks if the expression is solved or not
     * @return "true" if the expression has more steps, "false" if it doesn't
     */
    boolean hasMoreSteps();

    /**
     * solves the next due step of the expression
     * @return a string containing the expression up to that point
     * @throws NoMoreStepsException if the expression is already fully solved
     */
    String solveStep() throws NoMoreStepsException;
}
