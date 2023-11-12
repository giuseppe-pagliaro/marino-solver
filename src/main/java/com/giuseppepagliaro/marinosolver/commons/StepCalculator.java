package com.giuseppepagliaro.marinosolver.commons;

import java.lang.Math;

/**
 * Calculates the value of simple operations provided in string form, 
 * used by {@link com.giuseppepagliaro.marinosolver.commons.ProblemStep}.
 * @author Giuseppe Pagliaro
 * @version 1.1.0
 * @since 1.0.0
 */
public final class StepCalculator {
    private StepCalculator() {}

    /**
     * Calculates the sum of two values provided in string form.
     * @param n1 addend one;
     * @param n2 addend two.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String add(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) + Double.parseDouble(n2));
    }

    /**
     * Calculates the subtraction of two values provided in string form.
     * @param n1 the minuend;
     * @param n2 the subtractor.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String subtract(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) - Double.parseDouble(n2));
    }

    /**
     * Calculates the multiplication of two values provided in string form.
     * @param n1 multiplier one;
     * @param n2 multiplier two.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String multiply(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) * Double.parseDouble(n2));
    }

    /**
     * Calculates the division of two values provided in string form.
     * @param n1 the numerator;
     * @param n2 the denominator.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String divide(String n1, String n2) throws NumberFormatException {
        return "" + (Double.parseDouble(n1) / Double.parseDouble(n2));
    }

    /**
     * Calculates the power of two values provided in string form.
     * @param n1 the base;
     * @param n2 the exponent.
     * @return A string with the result.
     * @throws NumberFormatException when an invalid number is provided.
     */
    public static String power(String n1, String n2) throws NumberFormatException {
        return "" + (Math.pow(Double.parseDouble(n1), Double.parseDouble(n2)));
    }
}
