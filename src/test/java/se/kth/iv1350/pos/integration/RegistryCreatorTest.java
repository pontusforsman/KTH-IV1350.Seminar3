package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link RegistryCreator} class.
 */
public class RegistryCreatorTest {
    private RegistryCreator registryCreator;

    /**
     * Sets up a new RegistryCreator instance before each test.
     */
    @BeforeEach
    public void setUp() {
        registryCreator = new RegistryCreator();
    }

    /**
     * Cleans up after each test.
     */
    @AfterEach
    public void tearDown() {
        registryCreator = null;
    }

    /**
     * Tests that getInventoryRegistry returns a non-null InventoryRegistry.
     */
    @Test
    public void testGetInventoryRegistry() {
        assertNotNull(registryCreator.getInventoryRegistry(), "InventoryRegistry should not be null.");
    }

    /**
     * Tests that getAccountingRegistry returns a non-null AccountingRegistry.
     */
    @Test
    public void testGetAccountingRegistry() {
        assertNotNull(registryCreator.getAccountingRegistry(), "AccountingRegistry should not be null.");
    }
} 