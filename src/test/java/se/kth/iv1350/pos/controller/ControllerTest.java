package se.kth.iv1350.pos.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.integration.RegistryCreator;
import se.kth.iv1350.pos.integration.Printer;
import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.SaleItemDTO;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Controller} class.
 * Covers sale start, item entry, invalid item, multiple items, payment, and receipt printing.
 */
public class ControllerTest {
    private Controller controller;

    /**
     * Sets up a new Controller instance before each test.
     */
    @BeforeEach
    public void setUp() {
        controller = new Controller(new RegistryCreator(), new Printer());
    }

    /**
     * Cleans up after each test.
     */
    @AfterEach
    public void tearDown() {
        controller = null;
    }

    /**
     * Verifies that startSale initializes a new sale and enterItem adds an item.
     */
    @Test
    public void testStartSaleAndEnterItem() {
        assertTrue(controller.startSale(), "startSale should return true.");
        SaleItemDTO itemDTO = controller.enterItem("1");
        assertNotNull(itemDTO, "enterItem should return a SaleItemDTO for a valid item.");
        assertEquals("1", itemDTO.item().id(), "Item ID should match the entered ID.");
    }

    /**
     * Verifies that entering an invalid item ID returns null.
     */
    @Test
    public void testEnterInvalidItem() {
        controller.startSale();
        assertNull(controller.enterItem("invalid"), "Should return null for invalid item ID.");
    }

    /**
     * Verifies that adding multiple items and ending the sale works as expected.
     */
    @Test
    public void testAddMultipleItemsAndEndSale() {
        controller.startSale();
        controller.enterItem("1");
        controller.enterItem("2");
        assertTrue(controller.endSale().getValue() > 0, "Total should be greater than zero after adding items.");
    }

    /**
     * Verifies that payment and change calculation work as expected.
     */
    @Test
    public void testPaymentAndChange() {
        controller.startSale();
        controller.enterItem("1");
        controller.endSale();
        Amount payment = Amount.of(100);
        Amount change = controller.enterPayment(payment);
        assertNotNull(change, "Change should be returned after payment.");
        // The item with ID "1" has price 10 and VAT 0, so total is 10, change should be 90
        assertEquals(90.0, change.getValue(), 0.001, "Change should be payment minus total (VAT-inclusive).");
    }
} 