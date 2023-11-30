package com.giuseppepagliaro.marinosolver.parsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.giuseppepagliaro.marinosolver.exceptions.InvalidTokenSequenceException;
import com.giuseppepagliaro.marinosolver.exceptions.StepAlreadySolvedException;
import com.giuseppepagliaro.marinosolver.exceptions.StepNotYetSolvedException;

/**
 * Unit tests for {@link com.giuseppepagliaro.marinosolver.parsers.ProblemStep}.
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
        step1 = new ProblemStep("L0", false, stepExpr1);
        step2 = new ProblemStep("L1S0", true, stepExpr2);
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
    public void testMemorize1() {
        assertEquals("5*L1S0", step1.getExpressionStr());
        assertEquals("2+3+7+5+15", step2.getExpressionStr());
    }

    @Test
    public void testMemorize2() {
        step1.memorize(Arrays.asList("*", "2"));
        assertEquals("5*L1S0*2", step1.getExpressionStr());
    }

    @Test(expected = StepAlreadySolvedException.class)
    public void testMemorizeException1() {
        step2.memorize(Arrays.asList("*", "2"));
    }

    @Test(expected = InvalidTokenSequenceException.class)
    public void testMemorizeException2() {
        step2.memorize(Arrays.asList("*", "*", "2"));
    }

    @Test(expected = InvalidTokenSequenceException.class)
    public void testMemorizeException3() {
        step2.memorize(Arrays.asList("*", "2", "2"));
    }

    @Test(expected = InvalidTokenSequenceException.class)
    public void testMemorizeException4() {
        step2 = new ProblemStep("L1S0", true, Arrays.asList("*", "2"));
    }

    @Test(expected = InvalidTokenSequenceException.class)
    public void testMemorizeException5() {
        step1.memorize(Arrays.asList("-", "2"));
    }

    @Test
    public void testSolve() {
        HashMap<String, ProblemStep> mockProblemTree = new HashMap<>();
        mockProblemTree.put("L0", step1);
        mockProblemTree.put("L1S0", step2);
        step2.solve(1, mockProblemTree);
        step1.solve(2, mockProblemTree);

        assertEquals("32.0", step2.getResult());
        assertEquals("160.0", step1.getResult());

        assertTrue(step2.isSolved(1));
        assertTrue(step1.isSolved(2));
        assertFalse(step1.isSolved(1));

        assertFalse(step1.isSolved(0));
        assertFalse(step2.isSolved(0));

        assertTrue(step1.isSolved(3));
        assertTrue(step2.isSolved(3));
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

    @Test
    public void testIsATreeHash1() {
        assertTrue(ProblemStep.isATreeHash("L1111S123"));
    }

    @Test
    public void testIsATreeHash2() {
        assertFalse(ProblemStep.isATreeHash("hkjhabhasbda"));
    }

    @Test
    public void testIsATreeHash3() {
        assertFalse(ProblemStep.isATreeHash("16251537161"));
    }

    @Test
    public void testIsATreeHash4() {
        assertFalse(ProblemStep.isATreeHash("LS2615361"));
    }

    @Test
    public void testIsATreeHash5() {
        assertFalse(ProblemStep.isATreeHash("L2161S"));
    }

    @Test
    public void testAdd() {
        assertEquals("5.0", ProblemStep.add("2", "3"));
    }

    @Test
    public void testSubtract() {
        assertEquals("-1.0", ProblemStep.subtract("2", "3"));
    }

    @Test
    public void testMultiply() {
        assertEquals("6.0", ProblemStep.multiply("2", "3"));
    }

    @Test
    public void testDivide() {
        assertEquals("3.0", ProblemStep.divide("6", "2"));
    }

    @Test
    public void testPower() {
        assertEquals("8.0", ProblemStep.power("2", "3"));
    }
}
