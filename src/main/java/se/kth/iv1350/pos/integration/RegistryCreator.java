package se.kth.iv1350.pos.integration;

/**
 * Creates and provides access to external system registries such as {@link InventoryRegistry} and {@link AccountingRegistry}.
 * Used by the {@link se.kth.iv1350.pos.controller.Controller} to access integration layer systems.
 */
public class RegistryCreator {
    private final InventoryRegistry inventoryRegistry;
    private final AccountingRegistry accountingRegistry;

    /**
     * Creates a new instance of <code>RegistryCreator</code>.
     * Initializes the {@link InventoryRegistry} and {@link AccountingRegistry}.
     */
    public RegistryCreator() {
        inventoryRegistry = new InventoryRegistry();
        accountingRegistry = new AccountingRegistry();
    }

    /**
     * Returns the {@link InventoryRegistry} instance.
     *
     * @return The <code>InventoryRegistry</code> instance.
     */
    public InventoryRegistry getInventoryRegistry() {
        return inventoryRegistry;
    }

    /**
     * Returns the {@link AccountingRegistry} instance.
     *
     * @return The <code>AccountingRegistry</code> instance.
     */
    public AccountingRegistry getAccountingRegistry() {
        return accountingRegistry;
    }
} 