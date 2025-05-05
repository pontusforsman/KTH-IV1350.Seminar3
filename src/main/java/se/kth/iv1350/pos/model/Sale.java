package se.kth.iv1350.pos.model;

import se.kth.iv1350.pos.integration.ItemDTO;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a <code>Sale</code> transaction, containing items, totals, and receipt generation.
 * The <code>Sale</code> is managed by the {@link se.kth.iv1350.pos.controller.Controller Controller} and provides data via {@link SaleDTO}.
 * <p>
 * <b>Note:</b> Methods that encounter invalid operations (e.g., adding quantity with no items, invalid payment amount) will return <code>null</code>.
 */
public class Sale {
    private final LinkedHashMap<String, SaleItem> items;
    private final CashRegister cashRegister;
    private Amount total;
    private Amount totalVat;
    private Receipt receipt;

    /**
     * Creates a new instance of <code>Sale</code>.
     * Initializes an empty sale with no items and zero totals.
     */
    public Sale(CashRegister cashRegister) {
        this.items = new LinkedHashMap<>();
        this.cashRegister = cashRegister;
        this.total = Amount.zero();
        this.totalVat = Amount.zero();
    }

    /**
     * Adds an item to the current <code>Sale</code>.
     *
     * @param item The {@link ItemDTO} to add.
     * @return A {@link SaleItemDTO} with updated sale information.
     */
    public SaleItemDTO addItem(ItemDTO item) {
        if (items.containsKey(item.id())) {
            items.get(item.id()).incrementQuantity();
        } else {
            items.put(item.id(), new SaleItem(item, 1));
        }
        updateRunningTotal();
        return new SaleItemDTO(item, items.get(item.id()).getQuantity(), total, totalVat);
    }

    /**
     * Updates the quantity of the last entered item in the current <code>Sale</code>.
     *
     * @param quantity The <code>int</code> quantity to add.
     * @return A {@link SaleItemDTO} with updated sale information, or <code>null</code> if there are no items in the sale or the quantity is invalid.
     */
    public SaleItemDTO updateQuantity(int quantity) {
        if (items.isEmpty() || items.lastEntry() == null) {
            return null;
        }
        if (quantity < 0) {
            return null;
        }
        var lastEntry = items.lastEntry();
        if (quantity == 0) {
            items.remove(lastEntry.getKey());
            updateRunningTotal();
            return null;
        }
        lastEntry.getValue().updateQuantity(quantity);
        updateRunningTotal();
        return new SaleItemDTO(lastEntry.getValue().getItem(), lastEntry.getValue().getQuantity(), total, totalVat);
    }

    /**
     * Completes the <code>Sale</code> and returns the total cost.
     *
     * @return The total cost as an {@link Amount}.
     */
    public Amount completeSale() {
        return total;
    }

    /**
     * Registers a payment and returns the change.
     *
     * @param amountPaid The amount paid as an {@link Amount}.
     * @return The change as an {@link Amount}, or <code>null</code> if the payment amount is invalid (negative).
     */
    public Amount pay(Amount amountPaid) {
        if (amountPaid.getValue() < 0) {
            return null;
        }
        CashPayment cashPayment = new CashPayment(amountPaid);
        cashPayment.calculateTotalCost(this);
        cashRegister.updateBalance(cashPayment.getAmountPaid());
        Amount change = amountPaid.subtract(total);
        receipt = new Receipt(this, amountPaid, change);
        return change;
    }

    /**
     * Gets the {@link Receipt} for this <code>Sale</code>.
     *
     * @return The {@link Receipt} for this <code>Sale</code>.
     */
    public Receipt getReceipt() {
        return receipt;
    }

    /**
     * Gets the total cost for this sale.
     *
     * @return The total cost as an {@link Amount}.
     */
    public Amount getTotalCost() {
        return total;
    }

    /**
     * Gets the total VAT for this sale.
     *
     * @return The total VAT as an {@link Amount}.
     */
    public Amount getTotalVat() {
        return totalVat;
    }

    /**
     * Creates a {@link SaleDTO} representing this sale's data for transfer between layers.
     *
     * @return a new <code>SaleDTO</code> with all items, total, and VAT.
     */
    public SaleDTO toDTO() {
        List<SaleItemDTO> itemDTOs = items.values().stream().map(SaleItem::toDTO).toList();
        return new SaleDTO(itemDTOs, total, totalVat);
    }

    /**
     * Gets all items in this sale.
     *
     * @return An unmodifiable map of item IDs to {@link SaleItem}.
     */
    Map<String, SaleItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    private void updateRunningTotal() {
        total = Amount.zero();
        totalVat = Amount.zero();
        for (SaleItem item : items.values()) {
            total = total.add(item.getLineTotal());
            totalVat = totalVat.add(item.getLineTotalVat());
        }
    }
} 