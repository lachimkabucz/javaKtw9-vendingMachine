package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;

import java.util.Optional;
import java.util.Random;

public class VendingMachine {

    private final Configuration configuration;
    private final Long rowsCount;
    private final Long colsCount;
    private final Tray[][] trays;

    public VendingMachine(Configuration configuration) {
        this.configuration = configuration;
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

        Random random = new Random();
        for (int rowNo = 0; rowNo < rowsCount; rowNo++) {
            for (int colNo = 0; colNo < colsCount; colNo++) {
                if (random.nextInt(10) < 8) {
//                if (Math.random() < 0.8) { // another way to generate probability 0.8
                    generateTrayAtPosition(rowNo, colNo);
                }
            }
        }
    }
    /*
    Wylosuj dla każdej tacki cenę z zakresu od 1 do 10 zł (100 do 1000 groszy)
    Dla każdej pozycji automatu prawdopodobieństwo posiadania tacki wynosi 0,8
    Prawdopodobieństwo posiadania przez tackę produktu wynosi 0,5.
    Prawdopodobieństwo, że tacka posiada 2 produkty wynosi 0,1.
    Niech nazwą produktu będzie "Product <Symbol Tacki>"
    */
    private void generateTrayAtPosition(int rowNo, int colNo) {
        Random random = new Random();
        long price = random.nextInt(901) + 100;
        char letter = (char) ('A' + rowNo);
        int number = colNo + 1;
        String symbol = "" + letter + number;

        Tray.Builder trayBuilder = Tray.builder(symbol).price(price);
        int productProbability = random.nextInt(10);
        if (productProbability < 5) {
            trayBuilder =
                    trayBuilder.product(new Product("Product " + symbol));
        }
        if (productProbability < 1) {
            trayBuilder =
                    trayBuilder.product(new Product("Product " + symbol));
        }
        trays[rowNo][colNo] = trayBuilder.build();
    }

    public Optional<Tray> getTrayAtPosition(int rowNo, int colNo) {
        try {
            Tray tray = trays[rowNo][colNo];
            Optional<Tray> wrappedTray = Optional.ofNullable(tray);
            return wrappedTray;
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





}
