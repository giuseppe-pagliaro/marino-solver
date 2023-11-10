package com.giuseppepagliaro.marinosolver.solvers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.LinkedList;
import java.util.Arrays;

import org.junit.Test;

import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;
import com.giuseppepagliaro.marinosolver.parsers.Parser;

/**
 * Unit tests for {@link com.giuseppepagliaro.marinosolver.solvers.Solver}.
 */
public class SolverTest {
    private Solver solver;

    @Test
    public void testParser1() {
        try {
            solver = new ExpressionSolver(new Parser("(2+3)*2"));
        } catch (IncorrectProblemSyntaxException e) { }

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        assertFalse(solver.hasMoreSteps());

        assertEquals("(2.0+3.0)*2.0", solver.getBase());

        LinkedList<String> expectedHistory1 = new LinkedList<>(Arrays.asList(
            "(2.0+3.0)*2.0",
            "5.0*2.0"
        ));
        assertEquals(expectedHistory1, solver.getHistory());

        assertEquals("10.0", solver.getResult());

        assertEquals("5.0*2.0", solver.getStep(1));
    }

    @Test
    public void testParser2() {
        try {
            solver = new ExpressionSolver(new Parser("2+3*4*3-2+4*5"));
        } catch (IncorrectProblemSyntaxException e) { }

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        assertFalse(solver.hasMoreSteps());

        assertEquals("2.0+3.0*4.0*3.0-2.0+4.0*5.0", solver.getBase());

        LinkedList<String> expectedHistory2 = new LinkedList<>(Arrays.asList(
            "2.0+3.0*4.0*3.0-2.0+4.0*5.0",
            "2.0+36.0-2.0+4.0*5.0",
            "2.0+36.0-2.0+20.0"
        ));
        assertEquals(expectedHistory2, solver.getHistory());

        assertEquals("56.0", solver.getResult());

        assertEquals("2.0+36.0-2.0+4.0*5.0", solver.getStep(1));
    }

    @Test
    public void testParser3() { // TODO fails (verify)
        try {
            solver = new ExpressionSolver(new Parser("2+3*4*3-(2*4+5)"));
        } catch (IncorrectProblemSyntaxException e) { }

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        assertFalse(solver.hasMoreSteps());

        assertEquals("2.0+3.0*4.0*3.0-(2.0*4.0+5.0)", solver.getBase());

        LinkedList<String> expectedHistory3 = new LinkedList<>(Arrays.asList(
            "2.0+3.0*4.0*3.0-(2.0*4.0+5.0)",
            "2.0+3.0*4.0*3.0-(8.0+5.0)",
            "2.0+36.0-(8.0+5.0)",
            "2.0+36.0-13.0"
        ));
        assertEquals(expectedHistory3, solver.getHistory());

        assertEquals("25.0", solver.getResult());

        assertEquals("2.0+3.0*4.0*3.0-(8.0+5.0)", solver.getStep(1));
    }
}
