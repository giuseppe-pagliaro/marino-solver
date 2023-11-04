package com.giuseppepagliaro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.giuseppepagliaro.errors.NoMoreStepsException;

public class ExpressionEvaler implements Evaler{
    public ExpressionEvaler(String expression) {
        createExpressionTree(expression);
        
        expressionHistory = new LinkedList<>();
        expressionHistory.add(expression);
    }

    private static final String EXPRESSION_DELIMITERS = " +-*/()";
    private static final String LEVEL_LABEL = "L";
    private static final String STEP_LABEL = "S";

    private HashMap<String, ArrayList<String>> expressionTree;
    private LinkedList<String> expressionHistory;

    private void createExpressionTree(String expression) {
        StringTokenizer exprTokenizer = new StringTokenizer(expression.trim(), EXPRESSION_DELIMITERS, true);
        HashMap<String, ArrayList<String>> expressionTree = new HashMap<>();

        while (exprTokenizer.hasMoreTokens()) {
            String token = exprTokenizer.nextToken();

            if (token.equals(" ")) continue;

            // TODO
        }

        this.expressionTree = expressionTree;
    }

    @Override
    public String getSolution() {
        return expressionHistory.getLast();
    }

    @Override
    public String getBase() {
        return expressionHistory.getFirst();
    }

    @Override
    public LinkedList<String> getHistory() {
        return new LinkedList<>(expressionHistory);
    }

    @Override
    public boolean hasMoreSteps() {
        return expressionTree.size() != 1 || expressionTree.get("l0").size() != 1;
    }

    @Override
    public String solveStep() throws NoMoreStepsException {
        if (!hasMoreSteps()) throw new NoMoreStepsException();

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveStep'");
    }
}

/*
 *   " 2+ (22*3+2) /(2+142)  " ->
 * 
 *   "2+ (22*3+2) /(2+142)" ->
 * 
 *   [2, +, (, 22, *, 3, +, 2, ), /, (, 2, +, 142, )] -> (automa) ->
 *  
 *   L0: [2, +, L1S1]
 *   L1S1: [L2S1, /, L2S2]
 *   L2S1: [L3S1, +, 2]
 *   L2S2: [2, +, 142]
 *   L3S1: [22, *, 3]
 * 
 *   HashMap<String, ArrayList<String>>
 * 
 *   Esempio struttura dati:
 *   2+(2*(3+2)) diventa:
 * 
 *   L0: [2, +, L1S1]
 *   L1S1: [2, *, L1S2]
 *   L1S2: [3, +, 2]
 * 
 */