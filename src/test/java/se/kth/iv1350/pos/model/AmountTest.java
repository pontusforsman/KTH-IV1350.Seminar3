package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Amount} class.
 * Covers arithmetic operations, zero/negative/large values, and multiply logic.
 */
public class AmountTest {
    private Amount hundredAmount;
    private Amount emptyAmount;

    /**
     * Sets up a new Amount instance before each test.
     */
    @BeforeEach
    public void setUp() {
        hundredAmount = Amount.of(100);
        emptyAmount = Amount.of(0);

    }

    /**
     * Cleans up after each test.
     */
    @AfterEach
    public void tearDown() {
        hundredAmount = null;
        emptyAmount = null;
    }

    /**
     * Verifies that add and subtract methods work correctly.
     */
    @Test
    public void testAdd() {
        Amount toAdd = Amount.of(50);
        assertNotNull(toAdd);
        Amount result = hundredAmount.add(toAdd);
        assertEquals(150, result.getValue(), "Add should work correctly.");
    }

    @Test
    public void testAddEmpty() {
        Amount toAdd = Amount.of(10);
        assertNotNull(toAdd);
        Amount result = emptyAmount.add(toAdd);
        assertEquals(10, result.getValue(), "Adding 10 to zero should return 10.");
    }

    @Test
    public void testSubtract() {
        Amount toSubtract = Amount.of(30);
        assertNotNull(toSubtract);
        Amount result = hundredAmount.subtract(toSubtract);
        assertEquals(70, result.getValue(), "Subtract should work correctly.");
    }

    @Test
    public void testSubtractEmpty() {
        Amount toSubtract = Amount.of(10);
        assertNotNull(toSubtract);
        Amount result = emptyAmount.subtract(toSubtract);
        assertNull(result, "Subtracting 10 from zero should return null.");
    }

    /**
     * Verifies that zero amounts are handled correctly.
     */
    @Test
    public void testZeroAmount() {
        assertEquals(0, emptyAmount.getValue(), "Zero amount should have 0 as value.");
    }

    /**
     * Verifies that a negative amount returns null.
     */
    @Test
    public void testNegativeAmount() {
        Amount negativeAmount = Amount.of(-50);
        assertNull(negativeAmount, "Negative amount should return null.");
    }

    /**
     * Verifies that the multiply method works as expected.
     */
    @Test
    public void testMultiply() {
        double toMultiply = 2;
        Amount result = hundredAmount.multiply(toMultiply);
        assertEquals(200, result.getValue(), "Multiply should work correctly.");
    }
} 