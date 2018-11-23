package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;

import java.io.Serializable;
import java.util.Optional;
import java.util.Random;


public class VendingMachine implements Serializable {
    public static final long serialVersionUID = 1L;
    private final Long rowsCount;
    private final Long colsCount;
    private final Tray[][] trays;

    public VendingMachine(Configuration configuration) {
        rowsCount = configuration
                .getLongProperty(
                        "machine.size.rows",
                        6L);
        colsCount = configuration
                .getLongProperty(
                        "machine.size.cols",
                        4L);
        if (rowsCount <= 0 || rowsCount > 26) {
            throw new IllegalArgumentException(
                    "Row count " + rowsCount + " is invalid");
        }
        if (colsCount <= 0 || colsCount > 9) {
            throw new IllegalArgumentException(
                    "Col count " + colsCount + " is invalid");
        }
        trays = new Tray[rowsCount.intValue()][colsCount.intValue()];
    }


    public Optional<Tray> getTrayAtPosition(int rowNo, int colNo) {
        try {
            Tray tray = trays[rowNo][colNo];

            return Optional.ofNullable(tray);
        } catch (ArrayIndexOutOfBoundsException e) {

            return Optional.empty();
        }
    }

    public Long rowsCount() {
        return rowsCount;
    }

    public Long colsCount() {
        return colsCount;
    }


    public Optional<String> productNameAtPosition(int rowNo, int colNo) {
        Optional<Tray> tray = getTrayAtPosition(rowNo, colNo);
        if (tray.isPresent()) {

            return tray.get().firstProductName();
        } else {

            return Optional.empty();
        }
    }

    public Optional<Product> buyProductWithSymbol(String traySymbol) {

        if (traySymbol.length() != 2) {
            return Optional.empty();
        }

        char symbolLetter = traySymbol.toUpperCase().charAt(0);
        char symbolNumber = traySymbol.charAt(1);

        int rowNo = symbolLetter - 'A';
        int colNo = symbolNumber - '1';

        if (rowNo < 0 || rowNo >= rowsCount
                || colNo < 0 || colNo >= colsCount ) {

            return Optional.empty();
        }
        Tray tray = trays[rowNo][colNo];
        if (tray == null) {

            return Optional.empty();
        } else {

            return tray.buyProduct();
        }
    }

    public boolean placeTray(Tray tray) {
        String symbol = tray.getSymbol();
        if (symbol.length() != 2) {
            return false;
        }
        int rowNo = symbol.charAt(0) - 'A';
        int colNo = symbol.charAt(1) - '1';
        if (rowNo < 0 || rowNo >= rowsCount
                || colNo < 0 || colNo >= colsCount) {
            return false;
        } else if (trays[rowNo][colNo] == null) {
            trays[rowNo][colNo] = tray;
            return true;
        } else {
            return false;
        }
    }

    public Optional<Tray> removeTrayWithSymbol(String traySymbol) {
        if (traySymbol.length() != 2) {
            return Optional.empty();
        }
        int rowNo = traySymbol.charAt(0) - 'A';
        int colNo = traySymbol.charAt(1) - '1';
        Optional<Tray> tray = getTrayAtPosition(rowNo, colNo);
        if (tray.isPresent()) {
            trays[rowNo][colNo] = null;
        }
        return tray;
    }

    public boolean addProductToTray(String traySymbol, Product product){
        return getTrayForSymbol(traySymbol)
                .map(tray -> tray.addProduct(product))
                .orElse(false);
    }

    private Optional<Tray> getTrayForSymbol(String traySymbol) {
        if (traySymbol.length() != 2) {
            return Optional.empty();
        }
        int rowNo = traySymbol.charAt(0) - 'A';
        int colNo = traySymbol.charAt(1) - '1';
        return getTrayAtPosition(rowNo, colNo);
    }


}