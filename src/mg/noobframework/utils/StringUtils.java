package mg.noobframework.utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtils {
    public static String toUppurcaseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDate(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            java.util.Date utilDate = dateFormat.parse(str);

            Date sqlDate = new Date(utilDate.getTime());

            return sqlDate != null;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isEmail(String str) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return str.matches(emailRegex);
    }

    

}
