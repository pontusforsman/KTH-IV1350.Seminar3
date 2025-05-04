package se.kth.iv1350.pos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.Printer;

/**
 * Represents a <code>Receipt</code> for a completed <code>Sale</code>, including all sale details and formatting.
 * Created by {@link Sale} and printed by {@link Printer}.
 */
public class Receipt {
    private final Sale sale;
    private final Amount amountPaid;
    private final Amount change;

    /**
     * Creates a new instance representing the receipt of the specified sale.
     *
     * @param sale The sale proved by this receipt.
     * @param amountPaid How much was paid for the sale.
     * @param change The amount of change returned to the customer.
     */
    public Receipt(Sale sale, Amount amountPaid, Amount change) {
        this.sale = sale;
        this.amountPaid = amountPaid;
        this.change = change;
    }

    /**
     * Creates a formatted string with the content of the receipt.
     *
     * @return The receipt as a string.
     */
    public String createReceiptString() {
        StringBuilder builder = new StringBuilder();

        appendReceiptHeader(builder);
        appendSaleItems(builder);
        appendReceiptTotal(builder);
        appendPaymentInfo(builder);
        appendReceiptFooter(builder);

        return builder.toString();
    }

    private void appendReceiptHeader(StringBuilder builder) {
        appendLine(builder, "------------------- Begin receipt -------------------");
        appendLine(builder, "Time of Sale: " + getCurrentTime());
        endSection(builder);
    }

    private void appendSaleItems(StringBuilder builder) {
        Map<String, SaleItem> items = sale.getItems();
        
        for (SaleItem item : items.values()) {
            ItemDTO itemInfo = item.getItem();
            int quantity = item.getQuantity();
            double price = itemInfo.price();
            Amount lineTotal = item.getLineTotal();
            
            appendLine(builder, itemInfo.name() + " " + quantity + " x " + formatPrice(price) + " " + formatAmount(lineTotal));
        }
        endSection(builder);
    }

    private void appendReceiptTotal(StringBuilder builder) {
        appendLine(builder, "Total: " + formatAmount(sale.getTotalCost()));
        appendLine(builder, "VAT: " + formatPrice(sale.getTotalVat().getValue()));
        endSection(builder);
    }

    private void appendPaymentInfo(StringBuilder builder) {
        appendLine(builder, "Cash: " + formatPrice(amountPaid.getValue()) + " SEK");
        appendLine(builder, "Change: " + formatPrice(change.getValue()) + " SEK");
    }

    private void appendReceiptFooter(StringBuilder builder) {
        appendLine(builder, "------------------- End receipt ---------------------");
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }
    
    private String formatAmount(Amount amount) {
        if (amount == null) return "0:00 SEK";
        return formatPrice(amount.getValue()) + " SEK";
    }

    private String formatPrice(double price) {
        return String.format("%.2f", price).replace('.', ':');
    }

    private void appendLine(StringBuilder builder, String line) {
        builder.append(line);
        builder.append("\n");
    }

    private void endSection(StringBuilder builder) {
        builder.append("\n");
    }
} 