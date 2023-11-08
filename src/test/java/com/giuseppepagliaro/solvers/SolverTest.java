package com.giuseppepagliaro.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.LinkedList;
import java.util.Arrays;

import org.junit.Test;

import com.giuseppepagliaro.parsers.Parser;

/**
 * Unit tests for {@link com.giuseppepagliaro.solvers.Solver}.
 */
public class SolverTest {
    public SolverTest() {
        solver = new ExpressionSolver(new Parser("(2+3)*2"));

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }
    }

    private Solver solver;

    @Test
    public void testHasMoreSteps() {
        assertFalse(solver.hasMoreSteps());
    }

    @Test
    public void testGetBase() {
        assertEquals("(2.0+3.0)*2.0", solver.getBase());
    }

    @Test
    public void testGetHistory() {
        LinkedList<String> expectedHistory = new LinkedList<>(Arrays.asList(
            "(2.0+3.0)*2.0",
            "5.0*2"
        ));

        assertEquals(expectedHistory, solver.getHistory());
    }

    @Test
    public void testGetResult() {
        assertEquals("10.0", solver.getResult());
    }

    @Test
    public void testGetStep() {
        assertEquals("5.0*2.0", solver.getStep(1));
    }
}
