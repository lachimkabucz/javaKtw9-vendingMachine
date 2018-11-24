package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.controller.service.EmployeeService;

import java.util.Optional;
import java.util.Scanner;

public class EmployeeOperationController {

    private final EmployeeService employeeService;

    public EmployeeOperationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void addTray() {
        String traySymbol = getTraySymbolFromUser();
        Long trayPrice = getTrayPriceFromUser();

        Optional<String> errorMessage =
                employeeService.addTray(traySymbol, trayPrice);
        System.out.println(
                errorMessage.orElse("Tray has been added."));
    }

    public void removeTray() {
        String traySymbol = getTraySymbolFromUser();
        Optional<String> errorMessage =
                employeeService.removeTrayWithSymbol(traySymbol);
        System.out.println(
                errorMessage.orElse("Tray has been removed."));
    }

    public void addProducts() {
        String traySymbol = getTraySymbolFromUser();
        String productName = getProductNameFromUser();
        Integer quantity = getQuantityFromUser();

        Optional<String> errorMessage =
                employeeService.addProduct(traySymbol, productName, quantity);
        System.out.println(
                errorMessage.orElse("All products have been added"));
    }

    public void changePrice() {
        String traySymbol = getTraySymbolFromUser();
        Long updatedPrice = getTrayPriceFromUser();
        Optional<String> errorMessage = employeeService.changePrice(traySymbol,updatedPrice);
        if(errorMessage.isPresent()){
            System.out.println(errorMessage.get());
        }
        else {
            System.out.println("Price changed.");
        }

        // pobierz od użytkownika symbol tacki, nową cenę
        // wywołać odpowiednią metodę z serwisu
        // wyświetlić komunikat błedu lub potwierdzenie udanej
        // operacji
    }

    private String getTraySymbolFromUser() {
        System.out.print(" > Provide tray symbol: ");
        return getUserInput().toUpperCase();
    }

    private String getProductNameFromUser() {
        System.out.print(" > Provide product name: ");
        return getUserInput();
    }

    private Integer getQuantityFromUser() {
        Integer quantity = null;
        while (quantity == null) {
            System.out.print(" > Provide product quantity: ");
            try {
                quantity = Integer.parseInt(getUserInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
        return quantity;
    }

    private Long getTrayPriceFromUser() {
        Long price = null;
        while (price == null) {
            System.out.print(" > Provide tray price: ");
            try {
                price = Long.parseLong(getUserInput());
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Try again.");
            }
        }
        return price;
    }

    private String getUserInput() {
        return new Scanner(System.in).nextLine();
    }
}