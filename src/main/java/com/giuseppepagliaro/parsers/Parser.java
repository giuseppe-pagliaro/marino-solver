package com.giuseppepagliaro.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.giuseppepagliaro.exceptions.IncorrectProblemSyntaxException;
import com.giuseppepagliaro.exceptions.ProblemErrorMessage;

/**
 * Converts a problem in textual form to a problem tree that 
 * can be solved by a {@link com.giuseppepagliaro.solvers.Solver} child.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public class Parser {
    public Parser(String problem, ProblemOperator[] operators, boolean acceptParenthesis) throws IncorrectProblemSyntaxException {
        problemTree = new HashMap<>();
        problemTree.put("L0", new ArrayList<String>());
        
        levelToMaxStepReached = new HashMap<>();
        levelToMaxStepReached.put(0, 0);

        currentLevel = 0;

        String delimiters = " ";
        HashMap<String, ProblemOperator> operatorValueToOperator = new HashMap<>();
        for (ProblemOperator operator : operators) {
            delimiters += operator.getValue();
            operatorValueToOperator.put(operator.getValue(), operator);
        }

        if (acceptParenthesis)
            delimiters += "()";

        createProblemTree(problem, delimiters, operatorValueToOperator);
    }

    public Parser(String problem) throws IncorrectProblemSyntaxException {
        this(problem, ProblemOperator.selectAll(), true);
    }

    private static final String LEVEL_LABEL = "L";
    private static final String STEP_LABEL = "S";

    private HashMap<String, ArrayList<String>> problemTree;
    private HashMap<Integer, Integer> levelToMaxStepReached;

    private int currentLevel;

    public HashMap<String, ArrayList<String>> getProblemTree() {
        return new HashMap<>(problemTree);
    }

    public HashMap<Integer, Integer> getLevelToMaxStepReached() {
        return new HashMap<>(levelToMaxStepReached);
    }

    protected void createProblemTree(String problem, String delimiters, HashMap<String, ProblemOperator> operatorValueToOperator) {
        StringTokenizer probTokenizer = new StringTokenizer(problem.trim(), delimiters, true);
        ArrayList<String> cache = new ArrayList<>();
        String lastOperatorValue = null;
        String currentState = "I";

        int parLevel = 0;
        int tokenInd = -1;

        while (probTokenizer.hasMoreTokens()) {
            tokenInd++;
            String token = probTokenizer.nextToken();

            if (token.equals(" ")) continue;

            switch (currentState) {
                case "I":
                    if (operatorValueToOperator.containsKey(token))
                        ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);

                    switch (token) {
                        case "(":
                            parLevel++;
                            moveDown(cache);
                            continue;
                        
                        case ")":
                            parLevel--;
                            moveUp(cache, token, tokenInd);
                            continue;
                        
                        default: // Possible Number
                            cacheNum(cache, token, tokenInd);
                            currentState = "N";
                            continue;
                    }
                
                case "N":
                    if (operatorValueToOperator.containsKey(token)) {
                        if (lastOperatorValue == null ||
                            operatorValueToOperator.get(token).compareLevel(operatorValueToOperator.get(lastOperatorValue)) == 0) {
                            cache.add(token);
                        } else if (operatorValueToOperator.get(token).compareLevel(operatorValueToOperator.get(lastOperatorValue)) < 0) {
                            moveUp(cache, token, tokenInd);
                            cache.add(token);
                            memorize(cache);
                        } else {
                            memorize(cache, 1);
                            moveDown(cache);
                            memorize(cache);
                            memorize(token);
                        }

                        lastOperatorValue = token;
                        currentState = "OP";
                        continue;
                    }

                    switch (token) {
                        case "(":
                            parLevel++;
                            cache.add("*");
                            memorize(cache);
                            moveDown(cache);
                            currentState = "I";
                            continue;
                        
                        case ")":
                            parLevel--;
                            moveUp(cache, token, tokenInd);
                            currentState = "N";
                            continue;
                        
                        default: // Possible Number
                            cache.add("*");
                            cacheNum(cache, token, tokenInd);
                            continue;
                    }
                
                case "OP":
                    if (operatorValueToOperator.containsKey(token))
                        ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);

                    switch (token) {
                        case "(":
                            parLevel ++;
                            moveDown(cache);
                            currentState = "I";
                            continue;
                        
                        case ")":
                            ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);
                        
                        default: // Possible Number
                            cacheNum(cache, token, tokenInd);
                            currentState = "N";
                            continue;
                    }
            }
        }

        switch (currentState) {
            case "I":
                if (tokenInd == 0)
                    ProblemErrorMessage.EMPTY_PROBLEM.print(tokenInd);
                else
                    ProblemErrorMessage.PARENTHESIS_NOT_CLOSED.print(tokenInd);
            
            case "N":
                if (parLevel != 0) ProblemErrorMessage.PARENTHESIS_NOT_CLOSED.print(tokenInd);
                memorize(cache);
                return;
            
            case "OP":
                ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);
        }
    }

    private String buildTreeHashString() {
        if (currentLevel == 0) return LEVEL_LABEL + currentLevel;
        return LEVEL_LABEL + currentLevel + STEP_LABEL + levelToMaxStepReached.get(currentLevel);
    }

    /* Automaton Action */

    private void moveDown(ArrayList<String> cache) {
        // Adding step record.
        currentLevel++;
        int maxStep = levelToMaxStepReached.containsKey(currentLevel) ? levelToMaxStepReached.get(currentLevel) + 1 : 0;
        levelToMaxStepReached.put(currentLevel, maxStep);

        // Create new step.
        String newHashStr = buildTreeHashString();
        problemTree.put(newHashStr, new ArrayList<String>());

        // Add reference of the new step to the tree.
        currentLevel--;
        memorize(newHashStr);
        currentLevel++;
    }

    private void moveUp(ArrayList<String> cache, String token, int tokenInd) {
        if (token.equals(")") && currentLevel <= 0) {
            ProblemErrorMessage.PARENTHESIS_NEVER_OPENED.print(tokenInd);
        }
        else if (currentLevel <= 0) {
            // Adding step record.
            currentLevel++;
            int maxStep = levelToMaxStepReached.containsKey(currentLevel) ? levelToMaxStepReached.get(currentLevel) + 1 : 0;
            levelToMaxStepReached.put(currentLevel, maxStep);
            
            // Create new step and memorize cache.
            String newHashStr = buildTreeHashString();
            problemTree.put(buildTreeHashString(), new ArrayList<String>());
            memorize(cache);

            // Memorize new step reference.
            currentLevel--;
            memorize(newHashStr);
        } else {
            memorize(cache);
            currentLevel--;
        }
    }

    private void memorize(String constant) {
        problemTree.get(buildTreeHashString()).add(constant);
    }

    private void memorize(ArrayList<String> cache) {
        memorize(cache, 0);
    }
    
    private void memorize(ArrayList<String> cache, int offset) {
        List<String> poppedElements = cache.subList(0, cache.size() - offset);
        problemTree.get(buildTreeHashString()).addAll(poppedElements);
        cache.removeAll(poppedElements);
    }

    private void cacheNum(ArrayList<String> cache, String num, int tokenInd) {
        try {
            cache.add("" + Double.parseDouble(num));
        } catch (NumberFormatException e) {
            ProblemErrorMessage.NON_PARSABLE_NUMBER_TOKEN.print(tokenInd);
        }
    }
}
