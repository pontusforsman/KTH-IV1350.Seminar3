package se.kth.iv1350.pos.startup;

import se.kth.iv1350.pos.integration.RegistryCreator;
import se.kth.iv1350.pos.integration.Printer;

import java.io.PrintStream;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.view.View;

/**
 * Entry point for the Point of Sale (POS) application.
 * Initializes the system and starts the user interface.
 */
public class Main {
    public static void main(String[] args) {
        RegistryCreator registryCreator = new RegistryCreator();
        Printer printer = new Printer();
        Controller controller = new Controller(registryCreator, printer);
        PrintStream out = System.out;
        
        new View(controller, out).sampleExecution();

    }
} 