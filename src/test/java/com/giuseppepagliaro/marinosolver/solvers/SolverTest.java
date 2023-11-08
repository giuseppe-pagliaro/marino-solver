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
    public SolverTest() {
        try {
            solver1 = new ExpressionSolver(new Parser("(2+3)*2"));
        } catch (IncorrectProblemSyntaxException e ) { }

        while (solver1.hasMoreSteps()) {
            solver1.solveStep();
        }

        try {
            solver2 = new ExpressionSolver(new Parser("2*3+3+5*2*2"));
        } catch (IncorrectProblemSyntaxException e) { }

        while (solver2.hasMoreSteps()) {
            solver2.solveStep();
        }
    }

    private Solver solver1;
    private Solver solver2;

    @Test
    public void testHasMoreSteps() {
        assertFalse(solver1.hasMoreSteps());
        assertFalse(solver2.hasMoreSteps());
    }

    @Test
    public void testGetBase() {
        assertEquals("(2.0+3.0)*2.0", solver1.getBase());
        assertEquals("2.0*3.0+3.0+5.0*2.0*2.0", solver2.getBase());
    }

    @Test
    public void testGetHistory() {
        LinkedList<String> expectedHistory = new LinkedList<>(Arrays.asList(
            "(2.0+3.0)*2.0",
            "5.0*2.0"
        ));

        assertEquals(expectedHistory, solver1.getHistory());

        expectedHistory = new LinkedList<>(Arrays.asList(
            "2.0*3.0+3.0+5.0*2.0*2.0",
            "6.0+3.0+20.0"
        ));

        assertEquals(expectedHistory, solver2.getHistory());
    }

    @Test
    public void testGetResult() {
        assertEquals("10.0", solver1.getResult());
        assertEquals("29.0", solver2.getResult());
    }

    @Test
    public void testGetStep() {
        assertEquals("5.0*2.0", solver1.getStep(1));
        assertEquals("6.0+3.0+20.0", solver2.getStep(1));
    }
}
