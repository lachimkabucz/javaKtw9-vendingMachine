package pl.sdacademy.vending.util;

public class StringUtil {
    public static String adjustText(String text, Integer expectedLength) {
        String expandedText = text;
        while (expandedText.length() < expectedLength) {
            expandedText = " " + expandedText + " ";
        }
        return expandedText.substring(0, expectedLength);
    }

    public static String formatMoney(Long amount) {
        return formatMoneyIntegrals(amount)
                + ","
                + formatMoneyDecimals(amount);
    }

    private static String formatMoneyIntegrals(Long amount) {
        String integrals = Long.toString(amount / 100);
        StringBuilder formattedMoney = new StringBuilder();
        Integer charactersTillLastSpace = 0;
        for (int charIndex = integrals.length() - 1; charIndex >= 0; charIndex--) {
            charactersTillLastSpace++;
            formattedMoney = formattedMoney.append(integrals.charAt(charIndex));
            if (charactersTillLastSpace >= 3) {
                formattedMoney = formattedMoney.append(" ");
                charactersTillLastSpace = 0;
            }
        }
        return formattedMoney.reverse().toString().trim();
    }

    private static String formatMoneyDecimals(Long amount) {
        String decimals = Long.toString(amount % 100);
        if (decimals.length() < 2) {
            decimals = "0" + decimals;
        }
        return decimals;
    }
}