package com.giuseppepagliaro.marinosolver.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.giuseppepagliaro.marinosolver.exceptions.StepAlreadySolvedException;
import com.giuseppepagliaro.marinosolver.exceptions.StepNotYetSolvedException;

/**
 * Unit tests for {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
 */
public class ProblemStepTest {
    public ProblemStepTest() {
        stepExpr1 = Arrays.asList("5", "*", "L1S0");
        stepExpr2 = Arrays.asList("2", "+", "3", "+", "7", "+", "5", "+", "15");
    }

    private List<String> stepExpr1;
    private List<String> stepExpr2;

    private ProblemStep step1;
    private ProblemStep step2;

    @Before
    public void resetSteps() {
        step1 = new ProblemStep("L0", false);
        step1.memorize(stepExpr1);

        step2 = new ProblemStep("L1S0", true);
        step2.memorize(stepExpr2);
    }

    @Test
    public void testIsSolved() {
        HashMap<String, ProblemStep> mockProblemTree = new HashMap<>();
        mockProblemTree.put("L0", step1);
        mockProblemTree.put("L1S0", step2);

        step2.solve(1, mockProblemTree);

        assertTrue(step2.isSolved(1));
        assertFalse(step2.isSolved(0));
        assertFalse(step1.isSolved(1));
    }

    @Test
    public void testMemorize() {
        assertEquals("5*L1S0", step1.getExpressionStr());
        assertEquals("2+3+7+5+15", step2.getExpressionStr());
    }

    @Test(expected = StepAlreadySolvedException.class)
    public void testMemorizeException() {
        step2.solve(1, new HashMap<>());
        step2.memorize(Arrays.asList("-", "2"));
    }

    @Test
    public void testSolve() {
        HashMap<String, ProblemStep> mockProblemTree = new HashMap<>();
        mockProblemTree.put("L0", step1);
        mockProblemTree.put("L1S0", step2);

        step2.solve(1, mockProblemTree);
        assertEquals("32.0", step2.getResult());
        assertEquals(1, step2.getTimeSolved());

        step1.solve(2, mockProblemTree);
        assertEquals("160.0", step1.getResult());
        assertEquals(2, step1.getTimeSolved());
    }

    @Test(expected = StepNotYetSolvedException.class)
    public void testSolveException() {
        HashMap<String, ProblemStep> mockProblemTree = new HashMap<>();
        mockProblemTree.put("L0", step1);
        mockProblemTree.put("L1S0", step2);

        step1.solve(1, mockProblemTree);
    }

    @Test(expected = StepNotYetSolvedException.class)
    public void testGetResultException() {
        step2.getResult();
    }

    @Test(expected = StepNotYetSolvedException.class)
    public void testGetTimeSolvedException() {
        step2.getTimeSolved();
    }
}
