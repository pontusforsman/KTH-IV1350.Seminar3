package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.Receipt;

/**
 * This class is responsible for printing the {@link Receipt} to the console.
 */
public class Printer {
    /**
     * Prints the specified receipt.
     *
     * @param receipt The receipt string to print.
     */
    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }
} 