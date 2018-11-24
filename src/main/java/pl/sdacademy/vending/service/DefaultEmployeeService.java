package pl.sdacademy.vending.service;

import pl.sdacademy.vending.controller.service.EmployeeService;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.Configuration;

import java.util.Optional;

public class DefaultEmployeeService implements EmployeeService {
    private final VendingMachineRepository machineRepository;
    private final Configuration configuration;

    public DefaultEmployeeService(VendingMachineRepository machineRepository, Configuration configuration) {
        this.machineRepository = machineRepository;
        this.configuration = configuration;
    }

    @Override
    public Optional<String> addTray(String symbol, Long price) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        VendingMachine vendingMachine = loadedMachine
                .orElseGet(() -> new VendingMachine(configuration));
        Tray tray = Tray.builder(symbol).price(price).build();
        if (vendingMachine.placeTray(tray)) {
            machineRepository.save(vendingMachine);
            return Optional.empty();
        } else {
            return Optional.of("Could not add tray, check provided position");
        }
    }

    @Override
    public Optional<String> removeTrayWithSymbol(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Tray> removedTray = machine.removeTrayWithSymbol(traySymbol);
            if (removedTray.isPresent()) {
                machineRepository.save(machine);
                return Optional.empty();
            } else {
                return Optional.of("Tray could not be removed");
            }
        } else {
            return Optional.of("There is no vending machine.");
        }
    }

    @Override
    public Optional<String> addProduct(
            String traySymbol,
            String productName,
            Integer quantity) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (!loadedMachine.isPresent()) {
            return Optional.of(
                    "There is no vending machine, add one by creating tray");
        }
        VendingMachine machine = loadedMachine.get();
        for (int addedProductCount = 0; addedProductCount < quantity; addedProductCount++) {
            Product product = new Product(productName);
            if (!machine.addProductToTray(traySymbol, product)) {
                machineRepository.save(machine);
                return Optional.of(
                        "Could not add "
                                + (quantity - addedProductCount)
                                + " products");
            }
        }
        machineRepository.save(machine);
        return Optional.empty();
    }

    @Override
    public Optional<String> changePrice(String traySymbol, Long updatedPrice) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            boolean successful = machine.updatePriceForSymbol(traySymbol, updatedPrice);
            machineRepository.save(machine);
            if (successful) {
                return Optional.empty();
            } else {
                return Optional.of("Could not change price, check tray symbol");
            }
        }
        return Optional.of("There is no Vending Machine, create one first.");
    }
}