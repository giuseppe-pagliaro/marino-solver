package com.giuseppepagliaro.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

/**
 * Unit tests for {@link com.giuseppepagliaro.parsers.Parser}.
 */
public class ParserTest {
    Parser parser1;
    Parser parser2;
    Parser parser3;
    Parser parser4;
    Parser parser5;
    Parser parser6;

    HashMap<String, ArrayList<String>> expectedTree1;
    HashMap<Integer, Integer> expectedLevelToMaxStep1;

    HashMap<String, ArrayList<String>> expectedTree2;
    HashMap<Integer, Integer> expectedLevelToMaxStep2;

    HashMap<String, ArrayList<String>> expectedTree3;

    HashMap<String, ArrayList<String>> expectedTree4;

    @Before
    public void setUp() {
        expectedTree1 = new HashMap<>();
        expectedTree1.put("L0", new ArrayList<>(Arrays.asList("3.0", "+", "2.0")));

        expectedLevelToMaxStep1 = new HashMap<>();
        expectedLevelToMaxStep1.put(0, 0);

        expectedTree2 = new HashMap<>();
        expectedTree2.put("L0", new ArrayList<>(Arrays.asList("L1S0")));
        expectedTree2.put("L1S0", new ArrayList<>(Arrays.asList("3.0", "+", "2.0")));

        expectedLevelToMaxStep2 = new HashMap<>();
        expectedLevelToMaxStep2.put(0, 0);
        expectedLevelToMaxStep2.put(1, 0);

        expectedTree3 = new HashMap<>();
        expectedTree3.put("L0", new ArrayList<>(Arrays.asList("L1S0", "+", "5.0")));
        expectedTree3.put("L1S0", new ArrayList<>(Arrays.asList("3.0", "*", "2.0")));

        expectedTree4 = new HashMap<>();
        expectedTree4.put("L0", new ArrayList<>(Arrays.asList("3.0", "+", "L1S0")));
        expectedTree4.put("L1S0", new ArrayList<>(Arrays.asList("2.0", "*", "5.0")));
    }

    @Test
    public void testCreateProblemTree1() {
        parser1 = new Parser("3+2");

        assertEquals(expectedTree1, parser1.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser1.getLevelToMaxStepReached());
    }

    @Test
    public void testCreateProblemTree2() {
        parser2 = new Parser("(3+2)");

        assertEquals(expectedTree2, parser2.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser2.getLevelToMaxStepReached());
    }

    @Test
    public void testCreateProblemTree3() {
        parser3 = new Parser("  3+2 ");

        assertEquals(expectedTree1, parser3.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser3.getLevelToMaxStepReached());
    }

    @Test
    public void testCreateProblemTree4() {
        parser4 = new Parser("3 +  2");

        assertEquals(expectedTree1, parser4.getProblemTree());
        assertEquals(expectedLevelToMaxStep1, parser4.getLevelToMaxStepReached());
    }

    @Test
    public void testCreateProblemTree5() {
        parser4 = new Parser("3*2+5");

        assertEquals(expectedTree3, parser4.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser4.getLevelToMaxStepReached());
    }

    @Test
    public void testCreateProblemTree6() {
        parser4 = new Parser("3+2*5");

        assertEquals(expectedTree4, parser4.getProblemTree());
        assertEquals(expectedLevelToMaxStep2, parser4.getLevelToMaxStepReached());
    }

    // TODO add tests for sintax errors.
}
