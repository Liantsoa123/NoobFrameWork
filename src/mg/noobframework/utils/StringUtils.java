package mg.noobframework.utils;

public class StringUtils {
    public static String toUppurcaseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
