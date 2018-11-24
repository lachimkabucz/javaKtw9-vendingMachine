package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.controller.service.CustomerService;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.StringUtil;

import java.util.Optional;
import java.util.Scanner;

/**
 Top layer of classic architecture, that handles communication with user. It will contain all Customer related operations, that can be invoked.
 */
public class CustomerOperationController {
    private final CustomerService customerService;
    // setting, that defines, how many characters is tray width.
    private final Integer trayWidth = 12;

    /**
     Public constructor that set up all dependencies for this controller.
     */
    public CustomerOperationController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     Main method that is printing machine to the console.
     */
    public void printMachine() {
        Optional<VendingMachine> loadedMachine = customerService.loadMachineToPrint();
        if (!loadedMachine.isPresent()) {
            System.out.println("Vending Machine out of service");
            return;
        }
        VendingMachine machine = loadedMachine.get();
        // every row of machine contains trays. Every tray contains few properties, that has to be displayed in another line. Every line has to be completed for all trays at once.
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            // first line of trays contains its upper boundary
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printUpperBoundary(machine, rowNo, colNo);
            }
            System.out.println(); // going to next line after previous one is completed

            // second line will contain tray symbols
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(machine, rowNo, colNo);
            }
            System.out.println(); // going to next line after previous one is completed

            // third contain its name
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printName(machine, rowNo, colNo);
            }
            System.out.println(); // going to next line after previous one is completed

            // fourth is price
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPrice(machine, rowNo, colNo);
            }
            System.out.println(); // going to next line after previous one is completed

            // and last one is for lower boundary
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(machine, rowNo, colNo);
            }
            System.out.println(); // going to next line after previous one is completed
        } // all steps will be repeated for all rows of trays
    }

    public void buyProduct() {
        System.out.print(" > Tray Symbol: ");
        String traySymbol = new Scanner(System.in).nextLine();
        Optional<Product> boughtProduct =
                customerService.buyProductFromTray(traySymbol);
        if (boughtProduct.isPresent()) {
            System.out.println("Here is your " + boughtProduct.get().getName());
        } else {
            System.out.println("Out of stock");
        }
    }

    private void printUpperBoundary(VendingMachine machine, int rowNo, int colNo) {
        System.out.print(
                "+" // left upper corner of tray
                        + StringUtil.duplicateText("-", trayWidth) // multipying "-" character so it will match tray width
                        + "+"); // right upper corner of tray
    }

    private void printSymbol(VendingMachine machine, int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
        System.out.print(
                "|" // left boundary
                        + StringUtil.adjustText(traySymbol, trayWidth) // centering text
                        + "|"); // right boundary
    }

    private void printName(VendingMachine machine, int rowNo, int colNo) {
        Optional<String> productName = machine.productNameAtPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|"
                + StringUtil.adjustText(formattedName, trayWidth) // centering text
                + "|");
    }

    private void printPrice(VendingMachine machine, int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        Long price = tray.map(Tray::getPrice).orElse(0L);
        String formattedMoney = StringUtil.formatMoney(price); // converting money to text
        String centeredMoney = StringUtil.adjustText(formattedMoney, trayWidth); // centering text
        System.out.print("|" + centeredMoney + "|");
    }

    private void printLowerBoundary(VendingMachine machine, int rowNo, int colNo) {
        System.out.print(
                "+" // left lower corner of tray
                        + StringUtil.duplicateText("-", trayWidth) // centering text
                        + "+"); // right lower corner of tray
    }

}