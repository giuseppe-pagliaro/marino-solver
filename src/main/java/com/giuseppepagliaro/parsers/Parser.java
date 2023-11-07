package com.giuseppepagliaro.parsers;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Converts a problem in textual form to a problem tree that 
 * can be solved by a {@link com.giuseppepagliaro.solvers.Solver} child.
 * @author Giuseppe Pagliaro
 * @version 1.1.0
 * @since 1.0.0
 */
public interface Parser {
    /**
     * Gets the problem representation, needed by a {@link com.giuseppepagliaro.solvers.Solver} child 
     * to solve a problem.
     * @return A {@link java.util.HashMap} containing the problem tree.
     */
    HashMap<String, ArrayList<String>> getProblemTree();

    /**
     * Gets the steps info of the problem, needed by a {@link com.giuseppepagliaro.solvers.Solver} child 
     * to solve a problem.
     * @return A {@link java.util.HashMap} containing the steps info.
     */
    HashMap<Integer, Integer> getLevelToMaxStepReached();

    /**
     * Gets the depth of the problem tree.
     * @return The number of the maximum level reached.
     */
    int getMaxLevelReached();
}
