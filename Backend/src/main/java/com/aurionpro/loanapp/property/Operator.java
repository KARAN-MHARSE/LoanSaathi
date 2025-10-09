package com.aurionpro.loanapp.property;

public enum Operator {
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!=");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    // Evaluate numbers
    public boolean evaluate(double actual, double expected) {
        return switch (this) {
            case GREATER_THAN -> actual > expected;
            case GREATER_THAN_EQUAL -> actual >= expected;
            case LESS_THAN -> actual < expected;
            case LESS_THAN_EQUAL -> actual <= expected;
            case EQUAL -> actual == expected;
            case NOT_EQUAL -> actual != expected;
        };
    }

    // Evaluate any Object
    public boolean evaluate(Object actualValue, String expectedValue) {
        if (actualValue == null || expectedValue == null) return false;

        // Try numeric comparison first
        try {
            double actual = Double.parseDouble(actualValue.toString());
            double expected = Double.parseDouble(expectedValue);
            return evaluate(actual, expected);
        } catch (NumberFormatException e) {
            // Not a number, fall back to string/boolean
        }

        String actualStr = actualValue.toString().trim();
        String expectedStr = expectedValue.trim();

        // Boolean comparison
        if (actualStr.equalsIgnoreCase("true") || actualStr.equalsIgnoreCase("false")) {
            boolean actualBool = Boolean.parseBoolean(actualStr);
            boolean expectedBool = Boolean.parseBoolean(expectedStr);
            return switch (this) {
                case EQUAL -> actualBool == expectedBool;
                case NOT_EQUAL -> actualBool != expectedBool;
                default -> false; // Other operators not supported for booleans
            };
        }

        // String comparison
        return switch (this) {
            case EQUAL -> actualStr.equalsIgnoreCase(expectedStr);
            case NOT_EQUAL -> !actualStr.equalsIgnoreCase(expectedStr);
            default -> false; // Other operators not supported for strings
        };
    }
}
