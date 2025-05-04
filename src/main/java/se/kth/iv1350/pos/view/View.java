package se.kth.iv1350.pos.view;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.SaleItemDTO;

import java.io.PrintStream;

/**
 * Simulated user interface for the POS system. This class is intended for demonstration and testing purposes only.
 * It makes hardcoded calls to the {@link Controller} and prints all output to a configurable output stream (default is System.out).
 * <p>
 * This class represents the <code>View</code> layer in the application.
 */
public class View {
    private static final String ADD_ITEM_MSG = "Add 1 item with item id ";
    private static final String ADD_QUANTITY_MSG = "Add %d item with the same id:";
    private static final String ITEM_NOT_FOUND_MSG = "Item not found";
    private static final String NO_ITEM_TO_UPDATE_MSG = "No item to update";
    private static final String END_SALE_MSG = "End sale:";
    private static final String CASH_MSG = "Amount paid: %s SEK";
    private static final String CHANGE_MSG = "Change: %s";

    private final Controller controller;
    /**
     * Creates a View with a custom output stream (for testability).
     *
     * @param controller The controller to use.
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Simulates user input that drives the program execution.
     */
    public void sampleExecution() {
        displayStartSale();
        displayEnterItem("2");
        displayEnterItem("3");
        int quantityToSet = 3;
        displayEnterQuantity(quantityToSet);
        displayEndSale();
        double PAYMENT_AMOUNT = 200;
        displayChange(PAYMENT_AMOUNT);
    }

    /**
     * Displays the start of a sale.
     */
    private void displayStartSale() {
        StringBuilder builder = new StringBuilder();
        boolean result = controller.startSale();
        if (result) {
            appendLine(builder, "Sale started.");
        } else {
            appendLine(builder, "Sale not started.");
        }
        print(builder);
    }

    /**
     * Displays the running total with item details after adding an item to the sale.
     *
     * @param itemID The ID of the item to add.
     */
    private void displayEnterItem(String itemID) {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, ADD_ITEM_MSG + itemID + " :");
        var item = controller.enterItem(itemID);
        if (item == null) {
            appendLine(builder, ITEM_NOT_FOUND_MSG);
            print(builder);
            return;
        }
        displayItemDetails(builder, item);
        print(builder);
    }

    /**
     * Displays the running total with item details after entering a quantity.
     *
     * @param quantity The quantity to set.
     */
    private void displayEnterQuantity(int quantity) {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, String.format(ADD_QUANTITY_MSG, quantity));
        var updatedItem = controller.enterQuantity(quantity);
        if (updatedItem == null) {
            appendLine(builder, NO_ITEM_TO_UPDATE_MSG);
            print(builder);
            return;
        }
        displayItemDetails(builder, updatedItem);
        print(builder);
    }

    /**
     * Displays the item details and total cost.
     *
     * @param builder The {@link StringBuilder} to append the details to.
     * @param item    The SaleItemDTO containing item details.
     */
    private void displayItemDetails(StringBuilder builder, SaleItemDTO item) {
        appendLine(builder, "Item ID: " + item.item().id());
        appendLine(builder, "Item name: " + item.item().name());
        appendLine(builder, "Item cost: " + formatPrice(item.item().price()) + " SEK");
        appendLine(builder, "VAT: " + formatPercentage(item.item().vatRate()));
        appendLine(builder, "Item description: " + item.item().description());
        endSection(builder);
        appendLine(builder, "Total cost (incl VAT): " + formatAmount(item.total()));
        appendLine(builder, "Total VAT: " + formatAmount(item.totalVat()));
        endSection(builder);
    }

    /**
     * Displays the end of the sale and the total cost.
     */
    private void displayEndSale() {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, END_SALE_MSG);
        Amount total = controller.endSale();
        appendLine(builder, "Total cost (incl VAT): " + formatAmount(total));
        endSection(builder);
        print(builder);
    }

    /**
     * Displays the change after payment.
     *
     * @param paymentAmount The amount paid by the customer.
     */
    private void displayChange(double paymentAmount) {
        StringBuilder builder = new StringBuilder();
        appendLine(builder, String.format(CASH_MSG, formatPrice(paymentAmount)));
        Amount change = controller.enterPayment(Amount.of(paymentAmount));
        appendLine(builder, String.format(CHANGE_MSG, formatAmount(change)));
        endSection(builder);
        print(builder);
    }

    /**
     * Formats the amount to a string with the format "0:00 SEK".
     * Note: The colon is used instead of a decimal point to match assignment requirements.
     *
     * @param amount The amount to format.
     * @return The formatted string.
     */
    private String formatAmount(Amount amount) {
        if (amount == null) return "0:00 SEK";
        String amountStr = amount.toString();
        return amountStr.replace(".", ":");
    }

    /**
     * Formats the price to a string with the format "0:00".
     * Note: The colon is used instead of a decimal point to match assignment requirements.
     *
     * @param price The price to format.
     * @return The formatted string.
     */
    private String formatPrice(double price) {
        return String.format("%.2f", price).replace('.', ':');
    }

    /**
     * Formats the percentage to a string with the format "0%".
     *
     * @param percentage The percentage to format.
     * @return The formatted string.
     */
    private String formatPercentage(double percentage) {
        return (int) (percentage * 100) + "%";
    }

    /**
     * Appends a line to the StringBuilder.
     *
     * @param builder The StringBuilder to append to.
     * @param line    The line to append.
     */
    private void appendLine(StringBuilder builder, String line) {
        builder.append(line);
        builder.append("\n");
    }

    /**
     * Ends the section by appending a new line.
     *
     * @param builder The {@link StringBuilder} to append to.
     */
    private void endSection(StringBuilder builder) {
        builder.append("\n");
    }

    /**
     * Prints the contents of the StringBuilder to the configured output stream.
     *
     * @param builder The StringBuilder containing the output.
     */
    private void print(StringBuilder builder) {
        System.out.print(builder.toString());
    }
}
