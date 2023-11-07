package com.giuseppepagliaro.parsers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.giuseppepagliaro.commons.ProblemStep;
import com.giuseppepagliaro.exceptions.IncorrectProblemSyntaxException;

public class ObjectOrientedParserTest {
    ObjectOrientedParser parser;

    HashMap<String, ProblemStep> expectedTree1;
    HashMap<String, ProblemStep> expectedTree2;
    HashMap<String, ProblemStep> expectedTree3;
    HashMap<String, ProblemStep> expectedTree4;
    HashMap<String, ProblemStep> expectedTree5;
    HashMap<String, ProblemStep> expectedTree6;

    HashMap<Integer, Integer> expectedLevelToMaxStep1;
    HashMap<Integer, Integer> expectedLevelToMaxStep2;

    @Before
    public void setUp() {
        // Init ProblemTree

        expectedTree1 = new HashMap<>();
        expectedTree1.put("L0", new ProblemStep("L0"));
        expectedTree1.get("L0").memorize(Arrays.asList("3.0", "+", "2.0"));

        expectedTree2 = new HashMap<>();
        expectedTree2.put("L0", new ProblemStep("L0"));
        expectedTree2.get("L0").memorize(Arrays.asList("L1S0"));
        expectedTree2.put("L1S0", new ProblemStep("L1S0"));
        expectedTree2.get("L1S0").memorize(Arrays.asList("3.0", "+", "2.0"));

        expectedTree3 = new HashMap<>();
        expectedTree3.put("L0", new ProblemStep("L0"));
        expectedTree3.get("L0").memorize(Arrays.asList("L1S0", "+", "5.0"));
        expectedTree3.put("L1S0", new ProblemStep("L1S0"));
        expectedTree3.get("L1S0").memorize(Arrays.asList("3.0", "*", "2.0"));

        expectedTree4 = new HashMap<>();
        expectedTree4.put("L0", new ProblemStep("L0"));
        expectedTree4.get("L0").memorize(Arrays.asList("3.0", "+", "L1S0"));
        expectedTree4.put("L1S0", new ProblemStep("L1S0"));
        expectedTree4.get("L1S0").memorize(Arrays.asList("2.0", "*", "5.0"));

        expectedTree5 = new HashMap<>();
        expectedTree5.put("L0", new ProblemStep("L0"));
        expectedTree5.get("L0").memorize(Arrays.asList("3.0", "*", "L1S0"));
        expectedTree5.put("L1S0", new ProblemStep("L1S0"));
        expectedTree5.get("L1S0").memorize(Arrays.asList("2.0", "+", "3.0"));

        expectedTree6 = new HashMap<>();
        expectedTree6.put("L0", new ProblemStep("L0"));
        expectedTree6.get("L0").memorize(Arrays.asList("3.0", "*", "5.0"));

        // Init LevelToMaxStep

        expectedLevelToMaxStep1 = new HashMap<>();
        expectedLevelToMaxStep1.put(0, 0);

        expectedLevelToMaxStep2 = new HashMap<>();
        expectedLevelToMaxStep2.put(0, 0);
        expectedLevelToMaxStep2.put(1, 0);
    }

    @Test
    public void testCreateProblemTree1() {
        parser = new ObjectOrientedParser("3+2");

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree2() {
        parser = new ObjectOrientedParser("(3+2)");

        assertEquals(expectedTree2, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree3() {
        parser = new ObjectOrientedParser("  3+2 ");

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree4() {
        parser = new ObjectOrientedParser("3 +  2");

        assertEquals(expectedTree1, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree5() {
        parser = new ObjectOrientedParser("3*2+5");

        assertEquals(expectedTree3, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree6() {
        parser = new ObjectOrientedParser("3+2*5");

        assertEquals(expectedTree4, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree7() {
        parser = new ObjectOrientedParser("3*(2+3)");

        assertEquals(expectedTree5, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree8() {
        parser = new ObjectOrientedParser("3(2+3)");

        assertEquals(expectedTree5, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser.getLevelToMaxStepReached());
        assertEquals(1, parser.getMaxLevelReached());
    }

    @Test
    public void testCreateProblemTree9() {
        parser = new ObjectOrientedParser("3 5");

        assertEquals(expectedTree6, parser.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser.getLevelToMaxStepReached());
        assertEquals(0, parser.getMaxLevelReached());
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
            parser = new ObjectOrientedParser(problem);
        } catch(IncorrectProblemSyntaxException e) {
            actual = e.getMessage();
        }

        assertEquals(expected, actual);
    }
}
