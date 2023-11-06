package com.giuseppepagliaro.solvers;

import static com.giuseppepagliaro.commons.StringBuilders.buildTreeHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.giuseppepagliaro.exceptions.HistoryWasNotTrackedException;
import com.giuseppepagliaro.exceptions.NoMoreStepsException;
import com.giuseppepagliaro.parsers.Parser;

/**
 * Main contract for problem solvers of the Marino Solver library.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class Solver {
    public Solver(Parser parser, boolean saveHistory) {
        problemTree = parser.getProblemTree();
        levelToMaxStep = parser.getLevelToMaxStepReached();

        if (saveHistory) {
            problemHistory = new LinkedList<>();

            String problem = getProblem();
            problemHistory.add(problem);
            latestStep = problem;
        } else {
            latestStep = "Not Yet Solved";
        }
    }

    protected HashMap<String, ArrayList<String>> problemTree;
    protected HashMap<Integer, Integer> levelToMaxStep;
    protected LinkedList<String> problemHistory;
    protected String latestStep;

    /**
     * gets the latest step of the problem or the solution if it's solved
     * @return a String containing the step
     */
    public String getLatestStep() {
        return latestStep;
    }
    
    /**
     * gets the original state of the problem
     * @return a String containing the problem
     */
    public String getBase() throws HistoryWasNotTrackedException {
        if (problemHistory == null) throw new HistoryWasNotTrackedException();

        return problemHistory.getFirst();
    }

    /**
     * gets all the steps executed so far
     * @return a {@link java.util.LinkedList} containing the steps from oldest to newest
     */
    public LinkedList<String> getHistory() throws HistoryWasNotTrackedException {
        if (problemHistory == null) throw new HistoryWasNotTrackedException();

        return new LinkedList<>(problemHistory);
    }

    /**
     * checks if the problem is solved or not
     * @return "true" if the problem has more steps, "false" if it doesn't
     */
    public boolean hasMoreSteps() {
        return problemTree.size() != 1 || problemTree.get("l0").size() != 1;
    }

    /**
     * solves the next due step of the problem
     * @return a string containing the problem up to that point
     * @throws NoMoreStepsException if the problem is already fully solved
     */
    public abstract void solveStep() throws NoMoreStepsException;

    /**
     * Turns the problem tree into a string.
     * @return A string containing the problem.
     */
    protected String getProblem() {
        return dfsGetProblem("", 0, 0, 0);
    }

    private String dfsGetProblem(String problem, int level, int step, int tokenInd) {
        if (level == 0 && step == levelToMaxStep.get(0)) return problem;

        int levelToUpdate = level == 0 ? 0 : level - 1;
        String hashStepToUpdate = buildTreeHash(levelToUpdate, step);
        String token = problemTree.get(hashStepToUpdate).get(tokenInd);

        if (token != buildTreeHash(level, levelToMaxStep))
            return dfsGetProblem(problem + token, level, tokenInd == 2 ? step + 1 : step, tokenInd + 1);
        
        return dfsGetProblem(problem, levelToUpdate, step, tokenInd); // TODO fai caso incrementa lv
    }
}
