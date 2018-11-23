package pl.sdacademy.vending;

import java.util.Arrays;

public enum UserMenuSelection {
    BUY_PRODUCT(1, "Buy product"),
    EXIT(9, "Exit"),
    SERVICE_MENU(0, "Service menu");

    private final Integer optionNumber;
    private final String optionText;

    UserMenuSelection(Integer optionNumber, String optionText) {
        this.optionNumber = optionNumber;
        this.optionText = optionText;
    }

    public static UserMenuSelection selectionForOptionNumber(
            Integer requestedOptionNumber) {
        return Arrays.stream(values())
                .filter(enumValue ->
                        enumValue.getOptionNumber().equals(requestedOptionNumber))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown option number: " + requestedOptionNumber));
//        for (UserMenuSelection menuSelection : values()) {
//            if (menuSelection.getOptionNumber().equals(requestedOptionNumber)) {
//                return menuSelection;
//            }
//        }
//        throw new IllegalArgumentException("Unknown option number: " + requestedOptionNumber);
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionText() {
        return optionText;
    }
}