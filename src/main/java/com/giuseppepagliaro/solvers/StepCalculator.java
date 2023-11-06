package com.giuseppepagliaro.solvers;

import java.lang.Math;

import com.giuseppepagliaro.exceptions.InvalidStepException;

/**
 * Calculates the steps for a {@link com.giuseppepagliaro.solvers.Solver} child.
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

    /**
     * Gets a step array and calculates the result.
     * @param step a default Java array contaning two numbers and an operator (in the middle).
     * @return A string with the result.
     * @throws InvalidStepException when an invalid step is provided.
     */
    public static String calculateStep(String[] step) throws InvalidStepException {
        if (step.length != 3) throw new InvalidStepException();

        switch (step[1]) {
            case "+":
                return add(step[0], step[2]);
            
            case "-":
                return subtract(step[0], step[2]);
            
            case "*":
                return multiply(step[0], step[2]);
            
            case "/":
                return divide(step[0], step[2]);
            
            case "^":
                return power(step[0], step[2]);
            
            default:
                throw new InvalidStepException();
        }
    }

    /**
     * Returns a human-readable version of the step.
     * @param step a default Java array contaning containing the step tokens.
     * @return The step as a string.
     */
    public static String toString(String[] step) {
        String stepStr = "";

        for (String token : step) {
            stepStr += token;
        }

        return stepStr;
    }
}
