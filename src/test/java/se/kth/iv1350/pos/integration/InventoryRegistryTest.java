package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link InventoryRegistry} class.
 */
public class InventoryRegistryTest {
    private InventoryRegistry inventoryRegistry;

    /**
     * Sets up a new InventoryRegistry instance before each test.
     */
    @BeforeEach
    public void setUp() {
        inventoryRegistry = new InventoryRegistry();
    }

    /**
     * Cleans up after each test.
     */
    @AfterEach
    public void tearDown() {
        inventoryRegistry = null;
    }

    /**
     * Tests that findItemById returns the correct item.
     */
    @Test
    public void testFindItemById() {
        ItemDTO item = inventoryRegistry.findItemById("1");
        assertNotNull(item, "Item should be found in inventory.");
        assertEquals("1", item.id(), "Item ID should match.");
        assertEquals("Medicine", item.name(), "Item name should match.");
    }
} 