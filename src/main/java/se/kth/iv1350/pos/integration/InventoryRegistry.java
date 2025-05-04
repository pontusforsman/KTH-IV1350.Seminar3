package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.SaleDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulates an external inventory system, storing and retrieving item data.
 * Receives sale data as {@link SaleDTO} from the controller.
 */
public class InventoryRegistry {
    private final Map<String, ItemData> inventory = new HashMap<>();

    /**
     * Package-private constructor, used only by {@link RegistryCreator}.
     * Initializes inventory.
     */
    InventoryRegistry() {
        initializeInventory();
    }

    /**
     * Finds an item in the inventory by its ID.
     *
     * @param itemID The ID of the item to find.
     * @return An {@link ItemDTO} if found, or <code>null</code> if not found.
     */
    public ItemDTO findItemById(String itemID) {
        ItemData item = inventory.get(itemID);
        if (item == null) {
            return null;
        }
        return item.toDTO();
    }

    /**
     * Updates the quantity of an item in the inventory.
     * If the item ID is invalid, the quantity is negative, or the item is not found, this method does nothing.
     *
     * @param itemID   The ID of the item to update.
     * @param quantity The new quantity to set (must be non-negative).
     */
    public void updateQuantity(String itemID, int quantity) {
        if (itemID == null) {
            return;
        }
        if (quantity < 0) {
            return;
        }
        if (!inventory.containsKey(itemID)) {
            return;
        }
        inventory.get(itemID).updateQuantity(quantity);
    }

    private void initializeInventory() {
        inventory.put("1", new ItemData("1", "Medicine", "Pain relief medicine", 10, 0.0, 4));
        inventory.put("2", new ItemData("2", "Newspaper", "Aftonbladet", 20, 0.06, 6));
        inventory.put("3", new ItemData("3", "Egg", "Free-range eggs", 30, 0.12, 8));
        inventory.put("4", new ItemData("4", "Phone", "Smartphone", 40, 0.25, 8));
    }

    /**
     * Updates the inventory system with the completed sale data.
     * Currently, does not perform any actual operations.
     *
     * @param saleDTO The <code>SaleDTO</code> containing sale information.
     */
    public void updateInventory(SaleDTO saleDTO) {
        System.out.printf("[%s]: Inventory updated.%n", this.getClass().getSimpleName().toUpperCase());
    }

    /**
     * Represents a database item, including its ID, name, description, price, VAT rate, and quantity.
     */
    private static final class ItemData {
        private final String itemID;
        private final String name;
        private final String description;
        private final double price;
        private final double vatRate;
        private int quantity;

        private ItemData(String itemID, String name, String description, double price, double vatRate) {
            this.itemID = itemID;
            this.name = name;
            this.description = description;
            this.price = price;
            this.vatRate = vatRate;
            this.quantity = 0;
        }

        private ItemData(String itemID, String name, String description, double price, double vatRate, int quantity) {
            this.itemID = itemID;
            this.name = name;
            this.description = description;
            this.price = price;
            this.vatRate = vatRate;
            this.quantity = quantity;
        }

        private ItemDTO toDTO() {
            return new ItemDTO(itemID, name, description, price, vatRate);
        }

        void updateQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

} 