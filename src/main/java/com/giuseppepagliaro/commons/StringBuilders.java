package com.giuseppepagliaro.commons;

import java.util.HashMap;

import com.giuseppepagliaro.parsers.ProblemOperator;

public final class StringBuilders {
    private StringBuilders() {}

    private static final String LEVEL_LABEL = "L";
    private static final String STEP_LABEL = "S";

    public static String buildSwitchStr(String currentState, String token, String lastOperatorValue, HashMap<String, ProblemOperator> ops) {
        if (ops.containsKey(token)) {
            if (currentState != "N") return currentState + "-op_";

            if (lastOperatorValue == null || ops.get(token).compareLevel(ops.get(lastOperatorValue)) == 0)
                return "N-ops";
            
            if (ops.get(token).compareLevel(ops.get(lastOperatorValue)) < 0)
                return "N-opl";
            
            return "N-oph";
        }

        if ("()".contains(token)) return currentState + "-" + token;
        
        return currentState + "-" + "n";
    }

    public static String buildTreeHash(int currentLevel, HashMap<Integer, Integer> levelToMaxStepReached) {
        if (currentLevel == 0) return LEVEL_LABEL + currentLevel;
        return LEVEL_LABEL + currentLevel + STEP_LABEL + levelToMaxStepReached.get(currentLevel);
    }

    public static String buildTreeHash(int currentLevel, int currentStep) {
        if (currentLevel == 0) return LEVEL_LABEL + currentLevel;
        return LEVEL_LABEL + currentLevel + STEP_LABEL + currentStep;
    }
}
