package com.giuseppepagliaro.marinosolver.commons;

import java.util.HashMap;

/**
 * Common string factory for Marino Solver.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public final class StringBuilders {
    private StringBuilders() {}

    private static final String LEVEL_LABEL = "L";
    private static final String STEP_LABEL = "S";

    /**
     * Builds the string used to create a {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
     * @param currentLevel The current level of the problem tree.
     * @param levelToMaxStepReached A {@link java.util.HashMap} that maps a level to the numbers of steps for that level.
     * @return The hash string for the new {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
     */
    public static String buildTreeHash(int currentLevel, HashMap<Integer, Integer> levelToMaxStepReached) {
        if (currentLevel == 0) return LEVEL_LABEL + currentLevel;
        return LEVEL_LABEL + currentLevel + STEP_LABEL + levelToMaxStepReached.get(currentLevel);
    }

    /**
     * Builds the string used to create a {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
     * @param currentLevel The current level of the problem tree.
     * @param currentStep The current step of the problem tree.
     * @return The hash string for the new {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
     */
    public static String buildTreeHash(int currentLevel, int currentStep) {
        if (currentLevel == 0) return LEVEL_LABEL + currentLevel;
        return LEVEL_LABEL + currentLevel + STEP_LABEL + currentStep;
    }

    /**
     * Determines whether or not the given token is a tree hash.
     * @param token The token to evaluate.
     */
    public static boolean isATreeHash(String token) {
        return token.contains(LEVEL_LABEL);
    }
}
