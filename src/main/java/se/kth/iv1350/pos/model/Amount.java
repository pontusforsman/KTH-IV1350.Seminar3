package se.kth.iv1350.pos.model;

import java.math.BigDecimal;

/**
 * Represents a monetary <code>Amount</code> in SEK, supporting arithmetic operations.
 */
public class Amount {
    private static final String CURRENCY = "SEK";
    private final BigDecimal value;

    private Amount(BigDecimal value) {
        this.value = value;
    }

    /**
     * Creates an <code>Amount</code> object with a value of zero.
     *
     * @return An <code>Amount</code> object representing zero.
     */
    public static Amount zero() {
        return new Amount(BigDecimal.ZERO);
    }

    /**
     * Creates an <code>Amount</code> object with the specified value.
     *
     * @param value The value of the amount.
     * @return An <code>Amount</code> object representing the specified value,
     * or <code>null</code> if the value is negative.
     */
    public static Amount of(double value) {
        if (value < 0) {
            return null;
        }
        return new Amount(BigDecimal.valueOf(value));
    }

    /**
     * Creates an <code>Amount</code> object with the specified value.
     *
     * @return Te value of the <code>Amount</code> object as a double.
     */
    public double getValue() {
        return value.doubleValue();
    }

    /**
     * Adds the specified amount to this amount.
     *
     * @return The sum of this amount and the specified amount.
     */
    public Amount add(Amount amount) {
        return new Amount(this.value.add(amount.value));
    }

    /**
     * Subtracts the specified amount from this amount.
     *
     * @return The difference between this amount and the specified amount, or null if the result would be negative.
     */
    public Amount subtract(Amount amount) {
        BigDecimal result = this.value.subtract(amount.value);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }
        return new Amount(result);
    }

    /**
     * Multiplies this amount by the specified <code>double </code> multiplier.
     *
     * @param multiplier The multiplier to apply.
     * @return The product of this amount and the specified multiplier.
     */
    public Amount multiply(double multiplier) {
        return new Amount(this.value.multiply(new BigDecimal(multiplier)));
    }

    /**
     * Custom <code>toString</code> method to display the <code>Amount</code>.
     *
     * @return A string representation of the <code>Amount</code>, with two decimal places and the currency.
     */
    @Override
    public String toString() {
        return String.format("%.2f %s", this.value, CURRENCY);
    }
} 