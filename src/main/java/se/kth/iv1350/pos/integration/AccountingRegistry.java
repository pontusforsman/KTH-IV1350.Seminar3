package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.SaleDTO;

/**
 * Simulates an external <code>AccountingRegistry</code> system. Receives sale data as {@link se.kth.iv1350.pos.model.SaleDTO} from the controller.
 */
public class AccountingRegistry {
    private double totalRevenue;

    AccountingRegistry() {
    }

    /**
     * Updates the <code>AccountingRegistry</code> system with the completed sale data.
     * This method simulates updating an external accounting system by printing a message to the console.
     *
     * @param saleDTO The {@link SaleDTO} containing sale information.
     */
    public void updateAccounting(SaleDTO saleDTO) {
        double saleTotal = saleDTO.total().getValue();
        totalRevenue += saleTotal;

        System.out.printf("[%s]: Accounting updated. Total revenue: %.2f%n", this.getClass().getSimpleName().toUpperCase(), totalRevenue);
    }
} 