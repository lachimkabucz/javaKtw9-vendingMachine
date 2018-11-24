package pl.sdacademy.vending.controller.service;

import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.VendingMachine;

import java.util.Optional;

public interface CustomerService {
    Optional<Product> buyProductFromTray(String traySymbol);
    Optional<VendingMachine> loadMachineToPrint();

}
