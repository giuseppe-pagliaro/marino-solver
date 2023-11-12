package com.giuseppepagliaro.marinosolver.commons;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link com.giuseppepagliaro.marinosolver.commons.StepCalculator}.
 */
public class StepCalculatorTest {
    public StepCalculatorTest() {
        step1 = new String[] {"2", "+", "3"};
        step2 = new String[] {"2", "-", "3"};
        step3 = new String[] {"2", "*", "3"};
        step4 = new String[] {"6", "/", "2"};
        step5 = new String[] {"2", "^", "3"};
    }

    String[] step1;
    String[] step2;
    String[] step3;
    String[] step4;
    String[] step5;

    @Test
    public void testAdd() {
        assertEquals("5.0", StepCalculator.add("2", "3"));
    }

    @Test
    public void testSubtract() {
        assertEquals("-1.0", StepCalculator.subtract("2", "3"));
    }

    @Test
    public void testMultiply() {
        assertEquals("6.0", StepCalculator.multiply("2", "3"));
    }

    @Test
    public void testDivide() {
        assertEquals("3.0", StepCalculator.divide("6", "2"));
    }

    @Test
    public void testPower() {
        assertEquals("8.0", StepCalculator.power("2", "3"));
    }
}
