package pl.sdacademy.vending;

import java.util.Arrays;

public enum ServiceMenuSelection {
    ADD_TRAY(1, "Add new tray to machine"),
    REMOVE_TRAY(2, "Remove existing tray"),
    ADD_PRODUCT(3, "Add product to tray"),
    REMOVE_PRODUCT(4, "Remove product from tray"),
    CHANGE_PRICE(5, "Change tray price"),
    EXIT(9, "Exit to user menu");

    private final Integer optionNumber;
    private final String optionMessage;

    ServiceMenuSelection(
            Integer optionNumber,
            String optionMessage) {
        this.optionNumber = optionNumber;
        this.optionMessage = optionMessage;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionMessage() {
        return optionMessage;
    }

    public static ServiceMenuSelection selectionForOptionNumber(
            Integer requestedOptionNumber) {
        return Arrays.stream(values())
                .filter(enumValue ->
                        enumValue.getOptionNumber().equals(requestedOptionNumber))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown option number: " + requestedOptionNumber));
    }
}