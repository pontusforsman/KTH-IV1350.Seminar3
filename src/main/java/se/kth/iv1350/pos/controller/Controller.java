package se.kth.iv1350.pos.controller;

import se.kth.iv1350.pos.integration.*;
import se.kth.iv1350.pos.model.*;

/**
 * The {@link Controller} handles all calls to the model layer and coordinates the sale process.
 * It acts as the only entry point from the view to the model, ensuring low coupling between layers.
 * <p>
 * <b>Call order:</b> The following sequence must be followed:
 * <ol>
 *   <li>startSale()</li>
 *   <li>enterItem()/enterQuantity() (repeat as needed)</li>
 *   <li>endSale()</li>
 *   <li>enterPayment()</li>
 * </ol>
 * If a method is called out of order (e.g., before startSale()), it will return null or do nothing.
 */
public class Controller {
    private final InventoryRegistry inventoryRegistry;
    private final AccountingRegistry accountingRegistry;
    private final Printer printer;
    private final CashRegister cashRegister;
    private Sale currentSale;

    /**
     * Creates a new <code>Controller</code> instance, with the given {@link RegistryCreator}, {@link Printer}.
     * Also, instantiates the {@link CashRegister}.
     *
     * @param registryCreator Used to get access to external systems.
     * @param printer         Used to print the {@link Receipt}.
     */
    public Controller(RegistryCreator registryCreator, Printer printer) {
        inventoryRegistry = registryCreator.getInventoryRegistry();
        accountingRegistry = registryCreator.getAccountingRegistry();
        this.printer = printer;
        this.cashRegister = new CashRegister();
    }

    /**
     * Starts a new <code>Sale</code>.
     *
     * @return <code>true</code> if the <code>Sale</code> was successfully started.
     */
    public boolean startSale() {
        currentSale = new Sale(cashRegister);
        return true;
    }

    /**
     * Adds an item to the current {@link Sale}.
     *
     * @param itemID The <code>String</code> ID of the item to add.
     * @return Information about the added item as a {@link SaleItemDTO},
     * or <code>null</code> if the item was not found or no sale is started.
     */
    public SaleItemDTO enterItem(String itemID) {
        if (currentSale == null) {
            return null;
        }
        ItemDTO item = inventoryRegistry.findItemById(itemID);
        if (item == null) {
            return null;
        }
        return currentSale.addItem(item);
    }

    /**
     * Updates the quantity of the last entered item in the current {@link Sale}.
     *
     * @param quantity The <code>int</code> quantity to add.
     * @return Updated information about the item as a {@link SaleItemDTO} with new quantity,
     * or <code>null</code> if no sale is started.
     */
    public SaleItemDTO enterQuantity(int quantity) {
        if (currentSale == null) {
            return null;
        }
        return currentSale.addQuantity(quantity);
    }

    /**
     * Ends the current {@link Sale}.
     *
     * @return The total cost of the sale as an {@link Amount}, or <code>null</code> if no sale is started.
     */
    public Amount endSale() {
        if (currentSale == null) {
            return null;
        }
        return currentSale.completeSale();
    }

    /**
     * Handles a payment for the current <code>Sale</code>.
     * Including updating the cash register and printing the{@link Receipt}.
     * And updating the {@link AccountingRegistry} and {@link InventoryRegistry}.
     * Also prints the {@link Receipt} using the {@link Printer}.
     *
     * @param amountPaid The amount paid by the customer as an {@link Amount}.
     * @return The change to give back to the customer as an {@link Amount}, or <code>null</code> if no sale is started.
     */
    public Amount enterPayment(Amount amountPaid) {
        if (currentSale == null) {
            return null;
        }
        Amount change = currentSale.pay(amountPaid);

        updateRegistries();
        printReceipt();
        return change;
    }

    private void updateRegistries() {
        accountingRegistry.updateAccounting(currentSale.toDTO());
        inventoryRegistry.updateInventory(currentSale.toDTO());
    }

    /**
     * Prints the {@link Receipt} for the current <code>Sale</code> using the {@link Printer}.
     */
    private void printReceipt() {
        Receipt receipt = currentSale.getReceipt();
        if (receipt != null) {
            printer.printReceipt(currentSale.getReceipt().createReceiptString());
        }
    }
} 