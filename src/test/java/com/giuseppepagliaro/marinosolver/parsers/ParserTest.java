package com.giuseppepagliaro.marinosolver.parsers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import com.giuseppepagliaro.marinosolver.commons.ProblemStep;
import com.giuseppepagliaro.marinosolver.exceptions.IncorrectProblemSyntaxException;

/**
 * Unit tests for {@link com.giuseppepagliaro.marinosolver.parsers.Parser}.
 */
public class ParserTest {
    public ParserTest() {
        // Init ProblemTree

        expectedTree1 = new HashMap<>();
        expectedTree1.put("L0", new ProblemStep("L0", false));
        expectedTree1.get("L0").memorize(Arrays.asList("3.0", "+", "2.0"));

        expectedTree2 = new HashMap<>();
        expectedTree2.put("L0", new ProblemStep("L0", false));
        expectedTree2.get("L0").memorize(Arrays.asList("L1S0"));
        expectedTree2.put("L1S0", new ProblemStep("L1S0", true));
        expectedTree2.get("L1S0").memorize(Arrays.asList("3.0", "+", "2.0"));

        expectedTree3 = new HashMap<>();
        expectedTree3.put("L0", new ProblemStep("L0", false));
        expectedTree3.get("L0").memorize(Arrays.asList("L1S0", "+", "5.0"));
        expectedTree3.put("L1S0", new ProblemStep("L1S0", false));
        expectedTree3.get("L1S0").memorize(Arrays.asList("3.0", "*", "2.0"));

        expectedTree4 = new HashMap<>();
        expectedTree4.put("L0", new ProblemStep("L0", false));
        expectedTree4.get("L0").memorize(Arrays.asList("3.0", "+", "L1S0"));
        expectedTree4.put("L1S0", new ProblemStep("L1S0", false));
        expectedTree4.get("L1S0").memorize(Arrays.asList("2.0", "*", "5.0"));

        expectedTree5 = new HashMap<>();
        expectedTree5.put("L0", new ProblemStep("L0", false));
        expectedTree5.get("L0").memorize(Arrays.asList("3.0", "*", "L1S0"));
        expectedTree5.put("L1S0", new ProblemStep("L1S0", true));
        expectedTree5.get("L1S0").memorize(Arrays.asList("2.0", "+", "3.0"));

        expectedTree6 = new HashMap<>();
        expectedTree6.put("L0", new ProblemStep("L0", false));
        expectedTree6.get("L0").memorize(Arrays.asList("3.0", "*", "5.0"));

        expectedTree7 = new HashMap<>();
        expectedTree7.put("L0", new ProblemStep("L0", false));
        expectedTree7.get("L0").memorize(Arrays.asList("L1S0", "*", "5.0"));
        expectedTree7.put("L1S0", new ProblemStep("L1S0", true));
        expectedTree7.get("L1S0").memorize(Arrays.asList("2.0", "+", "3.0"));

        expectedTree8 = new HashMap<>();
        expectedTree8.put("L0", new ProblemStep("L0", false));
        expectedTree8.get("L0").memorize(Arrays.asList("2.0", "*", "5.0", "*", "4.0", "*", "3.0"));

        expectedTree9 = new HashMap<>();
        expectedTree9.put("L0", new ProblemStep("L0", false));
        expectedTree9.get("L0").memorize(Arrays.asList("2", "+", "L1S0", "-", "L1S1"));
        expectedTree9.put("L1S0", new ProblemStep("L1S0", false));
        expectedTree9.get("L1S0").memorize(Arrays.asList("3.0", "*", "4.0", "*", "3.0"));
        expectedTree9.put("L1S1", new ProblemStep("L1S1", true));
        expectedTree9.get("L1S1").memorize(Arrays.asList("L2S0", "+", "5.0"));
        expectedTree9.put("L2S0", new ProblemStep("L2S0", false));
        expectedTree9.get("L2S0").memorize(Arrays.asList("2.0", "*", "4.0"));

        // Init LevelToMaxStep

        expectedLevelToMaxStep1 = new HashMap<>();
        expectedLevelToMaxStep1.put(0, 0);

        expectedLevelToMaxStep2 = new HashMap<>();
        expectedLevelToMaxStep2.put(0, 0);
        expectedLevelToMaxStep2.put(1, 0);

        expectedLevelToMaxStep3 = new HashMap<>();
        expectedLevelToMaxStep3.put(0, 0);
        expectedLevelToMaxStep3.put(1, 1);
        expectedLevelToMaxStep3.put(2, 0);
    }

    private Parser parser;

    private HashMap<String, ProblemStep> expectedTree1;
    private HashMap<String, ProblemStep> expectedTree2;
    private HashMap<String, ProblemStep> expectedTree3;
    private HashMap<String, ProblemStep> expectedTree4;
    private HashMap<String, ProblemStep> expectedTree5;
    private HashMap<String, ProblemStep> expectedTree6;
    private HashMap<String, ProblemStep> expectedTree7;
    private HashMap<String, ProblemStep> expectedTree8;
    private HashMap<String, ProblemStep> expectedTree9;

    private HashMap<Integer, Integer> expectedLevelToMaxStep1;
    private HashMap<Integer, Integer> expectedLevelToMaxStep2;
    private HashMap<Integer, Integer> expectedLevelToMaxStep3;

    @Test
    public void testCreateProblemTree1() {
        try {
            parser = new Parser("3+2");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree2() {
        try {
            parser = new Parser("(3+2)");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree2, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree3() {
        try {
            parser = new Parser("  3+2 ");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree4() {
        try {
            parser = new Parser("3 +  2");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree5() {
        try {
            parser = new Parser("3*2+5");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree3, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree6() {
        try {
            parser = new Parser("3+2*5");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree4, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree7() {
        try {
            parser = new Parser("3*(2+3)");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree5, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree8() {
        try {
            parser = new Parser("3(2+3)");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree5, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree9() {
        try {
            parser = new Parser("3 5");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree6, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree10() {
        try {
            parser = new Parser("(2+3)*5");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree7, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree11() {
        try {
            parser = new Parser("2*5*4*3");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree8, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree12() { // TODO fails (verify)
        try {
            parser = new Parser("2+3*4*3-(2*4+5)");
        } catch (IncorrectProblemSyntaxException e) { }

        assertEquals(expectedTree9, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep3, parser.getLevelToMaxStepReached());
        assertEquals(2, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTreeError1() {
        testCreateProblemTreeError("2*(3+2", "Parenthesis should be closed at 5!");
    }

    @Test
    public void testCreateProblemTreeError2() {
        testCreateProblemTreeError("3*(2+3))", "Parenthesis closed at 7 but never opened!");
    }

    @Test
    public void testCreateProblemTreeError3() {
        testCreateProblemTreeError("13+2*", "Operator not followed by a number at 3!");
    }

    @Test
    public void testCreateProblemTreeError4() {
        testCreateProblemTreeError("1a+2", "The token at 0 can't be a number!");
    }

    @Test
    public void testCreateProblemTreeError5() {
        testCreateProblemTreeError("", "An empty problem was provided at 0!");
    }

    @Test
    public void testCreateProblemTreeError6() {
        testCreateProblemTreeError(" ", "An empty problem was provided at 0!");
    }

    private void testCreateProblemTreeError(String problem, String expected) {
        String actual = "Exception not thrown!";

        try {
            parser = new Parser(problem);
        } catch(IncorrectProblemSyntaxException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }
}
