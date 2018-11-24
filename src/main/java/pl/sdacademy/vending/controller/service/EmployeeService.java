package pl.sdacademy.vending.controller.service;

import pl.sdacademy.vending.model.Tray;

import java.util.Optional;

public interface EmployeeService {
    Optional<String> addTray(String symbol, Long price);
    Optional<String> removeTrayWithSymbol(String traySymbol);
    Optional<String> addProduct(String traySymbol, String productName, Integer quantity);
    Optional<String> changePrice(String traySymbol, Long updatedPrice);
}