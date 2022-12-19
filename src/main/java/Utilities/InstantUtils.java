package Utilities;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InstantUtils {
    public static final String PATTERN_FORMAT = "HH:mm dd-MM-yyyy";
    public static String instantToString (Instant instant) {
        return instantToString(instant, null);
    }
    public static String instantToString(Instant instant, String patternFomat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(patternFomat != null?patternFomat:PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return dateTimeFormatter.format(instant);
    }
    public static String doubleToVND(double value) {
        String pattern = ",###VND";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }
    public static String quantityProducts(double value) {
        String pattern = "### Gói";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }
}
