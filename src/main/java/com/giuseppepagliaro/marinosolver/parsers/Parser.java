package com.giuseppepagliaro.marinosolver.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.List;
import java.util.StringTokenizer;

import static com.giuseppepagliaro.marinosolver.commons.StringBuilders.buildTreeHash;
import static com.giuseppepagliaro.marinosolver.commons.StringBuilders.buildSwitchStr;

import com.giuseppepagliaro.marinosolver.commons.ProblemStep;
import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;
import com.giuseppepagliaro.marinosolver.exceptions.ProblemErrorMessage;

/**
 * Converts a problem in textual form to a problem tree that 
 * can be solved by a {@link com.giuseppepagliaro.marinosolver.solvers.Solver} child.
 * @author Giuseppe Pagliaro
 * @version 1.1.0
 * @since 1.0.0
 */
public class Parser{
    public Parser(String problem, ProblemOperator[] operators, boolean acceptParenthesis) throws IncorrectProblemSyntaxException {
        problemTree = new HashMap<>();
        problemTree.put("L0", new ProblemStep("L0", false));
        
        levelToMaxStepReached = new HashMap<>();
        levelToMaxStepReached.put(0, 0);

        currentLevel = 0;
        maxLevelReached = 0;

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

    public Parser(Parser original) {
        this.problemTree = new HashMap<>();
        Iterator<Entry<String, ProblemStep>> problemTreeIterator = original.problemTree.entrySet().iterator();
        while (problemTreeIterator.hasNext()) {
            Entry<String, ProblemStep> entry = problemTreeIterator.next();
            this.problemTree.put(entry.getKey(), new ProblemStep(entry.getValue()));
        }

        this.levelToMaxStepReached = new HashMap<>(original.levelToMaxStepReached);
        this.currentLevel = 0;
        this.maxLevelReached = original.maxLevelReached;
    }

    private HashMap<String, ProblemStep> problemTree;
    private HashMap<Integer, Integer> levelToMaxStepReached;

    private int currentLevel;
    private int maxLevelReached;

    /**
     * Gets the problem representation, needed by a 
     * {@link com.giuseppepagliaro.marinosolver.solvers.Solver} child to solve a problem.
     * @return A {@link java.util.HashMap} containing the problem tree.
     */
    public HashMap<String, ProblemStep> getProblemTree() {
        return new HashMap<>(problemTree);
    }

    /**
     * Gets the steps info of the problem, needed by a 
     * {@link com.giuseppepagliaro.marinosolver.solvers.Solver} child to solve a problem.
     * @return A {@link java.util.HashMap} containing the steps info.
     */
    public HashMap<Integer, Integer> getLevelToMaxStepReached() {
        return new HashMap<>(levelToMaxStepReached);
    }

    /**
     * Gets the depth of the problem tree.
     * @return The number that corresponds to the deepest steps.
     */
    public int getMaxLevelReached() {
        return maxLevelReached;
    }

    private void createProblemTree(String problem, String delimiters, HashMap<String, ProblemOperator> operatorValueToOperator)
        throws IncorrectProblemSyntaxException {
        StringTokenizer probTokenizer = new StringTokenizer(problem.trim(), delimiters, true);
        ArrayList<String> cache = new ArrayList<>();

        String switchStr = null;
        String lastOperatorValue = null;
        String currentState = "I";

        int parLevel = 0;
        int tokenInd = -1;

        while (probTokenizer.hasMoreTokens()) {
            tokenInd++;
            String token = probTokenizer.nextToken();

            if (token.equals(" ")) continue;

            switchStr = buildSwitchStr(currentState, token, lastOperatorValue, operatorValueToOperator);

            switch (switchStr) {
                // State I

                case "I-op_":
                    ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);

                case "I-(":
                    lastOperatorValue = null;
                    parLevel++;
                    moveDown(cache, true);
                    continue;
                
                case "I-)":
                    lastOperatorValue = null;
                    parLevel--;
                    moveUp(cache, token, tokenInd);
                    continue;
                
                case "I-n":
                    cacheNum(cache, token, tokenInd);
                    currentState = "N";
                    continue;

                // State N
                
                case "N-ops":
                    cache.add(token);
                    lastOperatorValue = token;
                    currentState = "OP";
                    continue;
                
                case "N-opl":
                    moveUp(cache, token, tokenInd);
                    cache.add(token);
                    memorize(cache);
                    lastOperatorValue = token;
                    currentState = "OP";
                    continue;

                case "N-oph":
                    memorize(cache, 1);
                    moveDown(cache, false);
                    memorize(cache);
                    memorize(token);
                    lastOperatorValue = token;
                    currentState = "OP";
                    continue;
                
                case "N-(":
                    lastOperatorValue = null;
                    parLevel++;
                    cache.add("*");
                    memorize(cache);
                    moveDown(cache, true);
                    currentState = "I";
                    continue;
                
                case "N-)":
                    lastOperatorValue = null;
                    parLevel--;
                    moveUp(cache, token, tokenInd);
                    currentState = "N";
                    continue;
                
                case "N-n":
                    cache.add("*");
                    cacheNum(cache, token, tokenInd);
                    continue;
                
                // State OP

                case "OP-op_":
                    ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);

                case "OP-(":
                    lastOperatorValue = null;
                    parLevel ++;
                    memorize(cache);
                    moveDown(cache, true);
                    currentState = "I";
                    continue;
                
                case "OP-)":
                    ProblemErrorMessage.INCOMPLETE_OPERATION.print(tokenInd);
                
                case "OP-n":
                    cacheNum(cache, token, tokenInd);
                    currentState = "N";
                    continue;
            }
        }

        // Halt Input Handler

        switch (currentState) {
            case "I":
                if (tokenInd == 0 || tokenInd == -1)
                    ProblemErrorMessage.EMPTY_PROBLEM.print(0);
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

    /* Automaton Actions */

    private void moveDown(ArrayList<String> cache, boolean isParenthesis) {
        currentLevel++;

        if (!levelToMaxStepReached.containsKey(currentLevel)) maxLevelReached++;

        // Adding step record.
        
        int maxStep = levelToMaxStepReached.containsKey(currentLevel) ? levelToMaxStepReached.get(currentLevel) + 1 : 0;
        levelToMaxStepReached.put(currentLevel, maxStep);

        // Create new step.
        String newHashStr = buildTreeHash(currentLevel, levelToMaxStepReached);
        problemTree.put(newHashStr, new ProblemStep(newHashStr, isParenthesis));

        // Add reference of the new step to the tree.
        currentLevel--;
        memorize(newHashStr);
        currentLevel++;
    }

    private void moveUp(ArrayList<String> cache, String token, int tokenInd) throws IncorrectProblemSyntaxException {
        if (token.equals(")") && currentLevel <= 0) {
            ProblemErrorMessage.PARENTHESIS_NEVER_OPENED.print(tokenInd);
        }
        else if (currentLevel <= 0) {
            if (!levelToMaxStepReached.containsKey(currentLevel)) maxLevelReached++;
            
            // Adding step record.
            currentLevel++;
            int maxStep = levelToMaxStepReached.containsKey(currentLevel) ? levelToMaxStepReached.get(currentLevel) + 1 : 0;
            levelToMaxStepReached.put(currentLevel, maxStep);
            
            // Create new step and memorize cache.
            String newHashStr = buildTreeHash(currentLevel, levelToMaxStepReached);
            problemTree.put(newHashStr, new ProblemStep(newHashStr, false));
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
        problemTree.get(buildTreeHash(currentLevel, levelToMaxStepReached)).memorize(new ArrayList<>(Arrays.asList(constant)));
    }

    private void memorize(ArrayList<String> cache) {
        memorize(cache, 0);
    }
    
    private void memorize(ArrayList<String> cache, int offset) {
        List<String> poppedElements = cache.subList(0, cache.size() - offset);
        problemTree.get(buildTreeHash(currentLevel, levelToMaxStepReached)).memorize(poppedElements);
        cache.removeAll(poppedElements);
    }

    private void cacheNum(ArrayList<String> cache, String num, int tokenInd) throws IncorrectProblemSyntaxException {
        try {
            cache.add("" + Double.parseDouble(num));
        } catch (NumberFormatException e) {
            ProblemErrorMessage.NON_PARSABLE_NUMBER_TOKEN.print(tokenInd);
        }
    }
}
