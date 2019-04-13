package perpustakaan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Rupiah {
    private static final DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("Rp");
        decimalFormatSymbols.setMonetaryDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');

        decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
    }

    public static String format(int number) {
        return decimalFormat.format(number);
    }
}
