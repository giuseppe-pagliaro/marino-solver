package com.giuseppepagliaro.marinosolver.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.Arrays;

import org.junit.Test;

import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;
import com.giuseppepagliaro.marinosolver.exceptions.StepNotYetReachedException;
import com.giuseppepagliaro.marinosolver.parsers.Parser;

/**
 * Unit tests for the exceptions of {@link com.giuseppepagliaro.marinosolver.solvers.Solver}.
 */
public class SolverExceptionsTest {
    public SolverExceptionsTest() {
        try {
            solver = new ExpressionSolver(new Parser("(2+3)*2"));
        } catch (IncorrectProblemSyntaxException e ) { }
        solver.solveStep();
    }

    private Solver solver;

    @Test
    public void testHasMoreSteps() {
        assertTrue(solver.hasMoreSteps());
    }

    @Test
    public void testGetHistory() {
        LinkedList<String> expectedHistory = new LinkedList<>(Arrays.asList(
            "(2.0+3.0)*2.0"
        ));

        assertEquals(expectedHistory, solver.getHistory());
    }

    @Test(expected = StepNotYetReachedException.class)
    public void testGetResult() {
        assertEquals("5.0", solver.getResult());
    }

    @Test
    public void testGetStep() {
        assertEquals("5.0*2.0", solver.getStep(1));
    }
}
