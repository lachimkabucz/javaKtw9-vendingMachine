package pl.sdacademy.vending;

import pl.sdacademy.vending.controller.CustomerOperationController;
import pl.sdacademy.vending.controller.EmployeeOperationController;
import pl.sdacademy.vending.controller.service.EmployeeService;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.repository.HardDriveVendingMachineRepository;
import pl.sdacademy.vending.service.DefaultEmployeeService;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.Configuration;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    Configuration configuration =
            new Configuration();
    VendingMachineRepository vendingMachineRepository =
            new HardDriveVendingMachineRepository(configuration);
    EmployeeService employeeService =
            new DefaultEmployeeService(vendingMachineRepository, configuration);
    EmployeeOperationController employeeOperationController =
            new EmployeeOperationController(employeeService);
    CustomerOperationController customerOperationController =
            new CustomerOperationController(vendingMachineRepository);

    private void startApplication() {
        while (true) {
            customerOperationController.printMachine();
            printMenu();
            try {
                UserMenuSelection userSelection = getUserSelection();
                switch (userSelection) {
                    case BUY_PRODUCT:
                        System.out.print(" > Tray Symbol: ");
                        String traySymbol = new Scanner(System.in).nextLine();
                        Optional<Product> boughtProduct =
                                customerOperationController.buyProductForSymbol(traySymbol);
                        if (boughtProduct.isPresent()) {
                            System.out.println("Here is your " + boughtProduct.get().getName());
                        } else {
                            System.out.println("Out of stock");
                        }
                        break;
                    case EXIT:
                        System.out.println("Bye");
                        return;
                    case SERVICE_MENU:
                        handleServiceUser();
                        break;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printMenu() {
        UserMenuSelection[] allPossibleSelection
                = UserMenuSelection.values();
        for (UserMenuSelection menuPosition : allPossibleSelection) {
            System.out.println(menuPosition.getOptionNumber()
                    + ". "
                    + menuPosition.getOptionText());
        }
    }

    private void handleServiceUser() {
        while (true) {
            customerOperationController.printMachine();
            printServiceMenu();
            ServiceMenuSelection selection =
                    getServiceUserSelection();
            switch (selection) {
                case ADD_TRAY:
                    employeeOperationController.addTray();
                    break;
                case REMOVE_TRAY:
                    employeeOperationController.removeTray();
                    break;
                case ADD_PRODUCT:
                    employeeOperationController.addProducts();
                    break;
                case REMOVE_PRODUCT:
                    break;
                case CHANGE_PRICE:
                    break;
                case EXIT:
                    System.out.println("Going back to user menu");
                    return;
            }
        }
    }

    private void printServiceMenu() {
        for (ServiceMenuSelection value : ServiceMenuSelection.values()) {
            System.out.println(
                    value.getOptionNumber()
                            + ". "
                            + value.getOptionMessage());
        }
    }

    private ServiceMenuSelection getServiceUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return ServiceMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid selection format");
        }
    }

    private UserMenuSelection getUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return UserMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid selection format");
        }
    }

    public static void main(String[] args) {
        new Main().startApplication();
    }
}