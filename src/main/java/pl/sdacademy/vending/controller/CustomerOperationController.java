package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.StringUtil;

import java.util.Optional;


public class CustomerOperationController {
    private final VendingMachineRepository machineRepository;

    private final Integer trayWidth = 12;


    public CustomerOperationController(
            VendingMachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public void printMachine() {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (!loadedMachine.isPresent()) {
            System.out.println("Vending Machine out of service");
            return;
        }
        VendingMachine machine = loadedMachine.get();
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printUpperBoundary(machine, rowNo, colNo);
            }
            System.out.println();

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

    public Optional<Product> buyProductForSymbol(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Product> boughtProduct =
                    machine.buyProductWithSymbol(traySymbol);
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            System.out.println("Vending Machine out of service");
            return Optional.empty();
        }
    }

    private void printUpperBoundary(VendingMachine machine, int rowNo, int colNo) {
        System.out.print(
                "+"
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