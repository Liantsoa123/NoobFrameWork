package mg.noobframework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import mg.noobframework.validation.Validation;

public class ObjectUtils {
    public static Object doSetter(Class<?> clazz, HttpServletRequest request, HashMap<String, String> error)
            throws Exception {
        Object obj = clazz.getConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String paramValue = request.getParameter(clazz.getSimpleName() + "." + fieldName);

            if (Validation.checkValidation(field, paramValue, request, error)) {
                Object parmaObject = convertValue(paramValue, field.getType());
                Method method = getSetterMethod(clazz, field);
                method.invoke(obj, parmaObject);
            }
        }

        if (error.size() > 0) {
            request.setAttribute("error", error);
        }

        return obj;
    }

    public static Method getSetterMethod(Class<?> clazz, Field field) throws Exception {
        String fieldName = field.getName();
        String setterName = "set" + StringUtils.toUppurcaseFirstLetter(fieldName);
        Method setMethod = clazz.getDeclaredMethod(setterName, field.getType());
        return setMethod;
    }

    public static Object convertValue(String value, Class<?> targetType) {
        // Gérer le cas où la valeur est null
        if (value == null || value.trim().isEmpty()) {
            if (targetType.isPrimitive()) {
                if (targetType == int.class)
                    return 0;
                if (targetType == long.class)
                    return 0L;
                if (targetType == double.class)
                    return 0.0;
                if (targetType == float.class)
                    return 0.0f;
                if (targetType == boolean.class)
                    return false;
                if (targetType == short.class)
                    return (short) 0;
                if (targetType == byte.class)
                    return (byte) 0;
                if (targetType == char.class)
                    return '\u0000';
            }
            return null;
        }

        try {
            if (targetType == String.class) {
                return value;
            } else if (targetType == int.class || targetType == Integer.class) {
                return Integer.parseInt(value.trim());
            } else if (targetType == long.class || targetType == Long.class) {
                return Long.parseLong(value.trim());
            } else if (targetType == boolean.class || targetType == Boolean.class) {
                return Boolean.parseBoolean(value.trim());
            } else if (targetType == double.class || targetType == Double.class) {
                return Double.parseDouble(value.trim());
            } else if (targetType == float.class || targetType == Float.class) {
                return Float.parseFloat(value.trim());
            } else if (targetType == short.class || targetType == Short.class) {
                return Short.parseShort(value.trim());
            } else if (targetType == byte.class || targetType == Byte.class) {
                return Byte.parseByte(value.trim());
            } else if (targetType == char.class || targetType == Character.class) {
                return value.charAt(0);
            } else if (targetType == java.sql.Date.class) {
                return java.sql.Date.valueOf(value.trim());
            } else if (targetType == java.sql.Timestamp.class) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(value.trim(), formatter);
                return Timestamp.valueOf(localDateTime);
            }
            throw new IllegalArgumentException("Type non supporté: " + targetType);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Erreur de conversion pour le type " + targetType + ": " + e.getMessage());
        }
    }

}
