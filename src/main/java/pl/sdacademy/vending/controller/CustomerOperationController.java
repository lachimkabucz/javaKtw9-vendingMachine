package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.model.VendingMachine;

public class CustomerOperationController {
    private VendingMachine machine;

    public CustomerOperationController() {
        machine = new VendingMachine();
    }

    public void printMachine() {
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                System.out.print("+--------+");
            }
            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                char symbolLetter = (char) ('A' + rowNo);
                int symbolNumber = colNo + 1;
                System.out.print("|   " + symbolLetter + symbolNumber + "   |");
            }
            System.out.println();

            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                System.out.print("+--------+");
            }
            System.out.println();
        }
    }

}
