package com.giuseppepagliaro.marinosolver.parsers;

/**
 * Defines all the available operators that can be injected in a 
 * {@link com.giuseppepagliaro.marinosolver.parsers.Parser} instance.
 * @author Giuseppe Pagliaro
 * @version 1.0.1
 * @since 1.0.0
 */
public enum ProblemOperator {
    PLUS("+", 1),
    MINUS("-", 1),
    TIMES("*", 2),
    DIVIDED("/", 2),
    POWER("^", 3);

    private ProblemOperator(String value, int level) {
        VALUE = value;
        this.level = level;
    }

    private final int level;

    public final String VALUE;

    /**
     * Compares the level of two operators.
     * @param other The other operator to compare to.
     * @return SAME if they have the same priority, HIGHER if "this" is of 
     * higher priority, LOWER if "this" is lower.
     */
    public Priority compareLevel(ProblemOperator other) {
        if (this.level - other.level == 0) return Priority.SAME;
        if (this.level - other.level > 0) return Priority.HIGHER;
        return Priority.LOWER;
    }

    public static ProblemOperator[] selectAll() {
        return new ProblemOperator[] {
            ProblemOperator.PLUS,
            ProblemOperator.MINUS,
            ProblemOperator.TIMES,
            ProblemOperator.DIVIDED,
            ProblemOperator.POWER
        };
    }

    public static String buildOperatorRegex(ProblemOperator[] operators) {
        String operatorRegex = "";

        for (ProblemOperator operator : operators) {
            operatorRegex += "\\" + operator.VALUE;
        }

        return "[" + operatorRegex + "]";
    }

    public enum Priority {
        HIGHER,
        LOWER,
        SAME
    }
}
