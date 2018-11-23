package pl.sdacademy.vending.util;

/**
 * Helper class that provides static methods for string manipulations.
 */
public class StringUtil {

    /**
     * Duplicates provided text for specified amount of times.
     */
    public static String duplicateText(String text, Integer count) {
        StringBuilder duplicatedText = new StringBuilder();
        for (int i = 0; i < count; i++) {
            duplicatedText = duplicatedText.append(text);
        }
        return duplicatedText.toString();
    }

    /**
     * Centers or trims provided text, so it will fit provided width
     */
    public static String adjustText(String text, Integer expectedLength) {
        String expandedText = text;
        while (expandedText.length() < expectedLength) {
            expandedText = " " + expandedText + " ";
        }
        return expandedText.substring(0, expectedLength);
    }

    /**
     * Adds comma to distinguish integrals and decimals in money. Adds space every 3 digits of integrals.
     */
    public static String formatMoney(Long amount) {
        return formatMoneyIntegrals(amount)
                + ","
                + formatMoneyDecimals(amount);
    }

    private static String formatMoneyIntegrals(Long amount) {
        // remove decimals ("grosze")
        String integrals = Long.toString(amount / 100);
        StringBuilder formattedMoney = new StringBuilder();
        Integer charactersTillLastSpace = 0;
        // iterating from end of the value
        for (int charIndex = integrals.length() - 1; charIndex >= 0; charIndex--) {
            charactersTillLastSpace++;
            formattedMoney = formattedMoney.append(integrals.charAt(charIndex));
            if (charactersTillLastSpace >= 3) {
                // adding space every 3 digits
                formattedMoney = formattedMoney.append(" ");
                charactersTillLastSpace = 0;
            }
        }
        // as we were iterating from the end of value, result is reversed. We have to reverse it once more and remove spaces on the ends
        return formattedMoney.reverse().toString().trim();
    }

    private static String formatMoneyDecimals(Long amount) {
        // removing all integrals ("zlotowki")
        String decimals = Long.toString(amount % 100);
        if (decimals.length() < 2) {
            decimals = "0" + decimals;
        }
        return decimals;
    }
}