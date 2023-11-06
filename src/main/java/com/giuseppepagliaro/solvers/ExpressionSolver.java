package com.giuseppepagliaro.solvers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.giuseppepagliaro.exceptions.HistoryWasNotTrackedException;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.parsers.Parser;

public class ExpressionSolver implements Solver{
    public ExpressionSolver(String expression, boolean saveHistory) {
        Parser parser = new Parser(expression);
        expressionTree = parser.getProblemTree();

        if (saveHistory) expressionHistory = new LinkedList<>();
        expressionHistory.add(expression);
    }

    private HashMap<String, ArrayList<String>> expressionTree;
    private LinkedList<String> expressionHistory;
    private String latestStep;

    @Override
    public String getLatestStep() {
        return latestStep;
    }

    @Override
    public String getBase() throws HistoryWasNotTrackedException {
        if (expressionHistory == null) throw new HistoryWasNotTrackedException();

        return expressionHistory.getFirst();
    }

    @Override
    public LinkedList<String> getHistory() throws HistoryWasNotTrackedException {
        if (expressionHistory == null) throw new HistoryWasNotTrackedException();
        
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