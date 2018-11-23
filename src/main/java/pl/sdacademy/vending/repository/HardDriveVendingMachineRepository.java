package pl.sdacademy.vending.repository;

import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.service.repository.VendingMachineRepository;
import pl.sdacademy.vending.util.Configuration;

import java.io.*;
import java.util.Optional;

public class HardDriveVendingMachineRepository
        implements VendingMachineRepository {

    private final String repoLocation;

    public HardDriveVendingMachineRepository(
            Configuration configuration
    ) {
        repoLocation = configuration.getStringProperty(
                "repository.harddrive.vm.path",
                "VendingMachine.ser"
        );
    }

    @Override
    public VendingMachine save(VendingMachine machine) {
        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(
                             new FileOutputStream(
                                     repoLocation))) {
            objectOutputStream.writeObject(machine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return machine;
    }

    @Override
    public Optional<VendingMachine> load() {
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(
                             new FileInputStream(repoLocation))) {
            VendingMachine machine =
                    (VendingMachine) objectInputStream.readObject();
            return Optional.ofNullable(machine);
        } catch (IOException e) {
            System.out.println("Vending Machine Repo file not found");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find Vending Machine class");
        }
        return Optional.empty();
    }
}