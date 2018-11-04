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

                // 0 1 2 3 4 5 6 7 8 9
                if(random.nextInt(10) <8){

                //if (Math.random() < 0.8) { //propability 0.8

                    generateTrayAtPosition(rowNo, colNo);
                }
            }
        }
    }

    private void generateTrayAtPosition(int rowNo, int colNo) {
        Random random = new Random();

        long price = random.nextInt(901) + 100;

        char letter = (char) ('A' + rowNo);
        int number = colNo + 1;
        String symbol = "" + letter + number;
        int productProbability = random.nextInt(10);
        if(productProbability < 1 ){
            //2produkty
            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .product(new Product("Product"+symbol))
                    .product(new Product("Product"+symbol))
                    .build();
            trays[rowNo][colNo] = tray;

        }
        else if (productProbability < 5) {
            //1product

            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .product(new Product("Product"+symbol))
                    .build();
            trays[rowNo][colNo] = tray;


        }
        else  {

            Tray tray = Tray
                    .builder(symbol)
                    .price(price)
                    .build();
            trays[rowNo][colNo] = tray;

        }

//        Tray tray = Tray
//                .builder(symbol)
//                .price(price)
//                .build();
//        //0.1 -> 2 product
//        //0.5 -> 1 product
//
//        trays[rowNo][colNo] = tray;
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
}
