package com.giuseppepagliaro.marinosolver.parsers;

/**
 * Defines all the available operators that can be injected in a 
 * {@link com.giuseppepagliaro.marinosolver.parsers.Parser} instance.
 * @author Giuseppe Pagliaro
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ProblemOperator {
    PLUS("+", 1),
    MINUS("-", 1),
    TIMES("*", 2),
    DIVIDED("/", 2),
    POWER("^", 3);

    private ProblemOperator(String value, int level) {
        this.value = value;
        this.level = level;
    }

    private final String value;
    private final int level;

    public String getValue() {
        return value;
    }

    public int compareLevel(ProblemOperator problemOperator) {
        return this.level - problemOperator.level;
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
}
