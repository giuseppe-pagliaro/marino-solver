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
        LinkedList<String> expHistory = new LinkedList<>(Arrays.asList(
            "(2.0+3.0)*2.0",
            "5.0*2.0"
        ));
        testParser("(2+3)*2", "(2.0+3.0)*2.0", "5.0*2.0", "10.0", expHistory);
    }

    @Test
    public void testParser2() {
        LinkedList<String> expHistory = new LinkedList<>(Arrays.asList(
            "2.0+3.0*4.0*3.0-2.0+4.0*5.0",
            "2.0+36.0-2.0+4.0*5.0",
            "2.0+36.0-2.0+20.0"
        ));
        testParser("2+3*4*3-2+4*5", "2.0+3.0*4.0*3.0-2.0+4.0*5.0", "2.0+36.0-2.0+4.0*5.0", "56.0", expHistory);
    }

    @Test
    public void testParser3() {
        LinkedList<String> expHistory = new LinkedList<>(Arrays.asList(
            "2.0+3.0*4.0*3.0-(2.0*4.0+5.0)",
            "2.0+3.0*4.0*3.0-(8.0+5.0)",
            "2.0+36.0-(8.0+5.0)",
            "2.0+36.0-13.0"
        ));
        testParser("2+3*4*3-(2*4+5)", "2.0+3.0*4.0*3.0-(2.0*4.0+5.0)", "2.0+3.0*4.0*3.0-(8.0+5.0)", "25.0", expHistory);
    }

    @Test
    public void testParser4() {
        LinkedList<String> expHistory = new LinkedList<>(Arrays.asList(
            "5.0+(4.0-6.0)",
            "5.0+(-2.0)"
        ));
        testParser("5+(4-6)", "5.0+(4.0-6.0)", "5.0+(-2.0)", "3.0", expHistory);
    }

    @Test
    public void testParser5() {
        LinkedList<String> expHistory = new LinkedList<>(Arrays.asList(
            "5.0*(4.0-6.0)",
            "5.0*(-2.0)"
        ));
        testParser("5*(4-6)", "5.0*(4.0-6.0)", "5.0*(-2.0)", "-10.0", expHistory);
    }

    private void testParser(String problem, String expBase, String expStep1, String expResult, LinkedList<String> expHistory) {
        try {
            solver = new ExpressionSolver(new Parser(problem));
        } catch (IncorrectProblemSyntaxException e) { }

        while (solver.hasMoreSteps()) {
            solver.solveStep();
        }

        assertFalse(solver.hasMoreSteps());
        assertEquals(expBase, solver.getBase());
        assertEquals(expHistory, solver.getHistory());
        assertEquals(expResult, solver.getResult());
        assertEquals(expStep1, solver.getStep(1));
    }
}
