package mg.noobframework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
            String paramValue = request.getParameter(fieldName);

            if (paramValue != null && Validation.checkValidation(field, paramValue, request, error)) {
                Object parmaObject = convertValue(paramValue, field.getType());
                Method method = getSetterMethod(clazz, field);
                method.invoke(obj, parmaObject);
            } else {
                obj = null;
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
        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.parseFloat(value);
        } else if (targetType == short.class || targetType == Short.class) {
            return Short.parseShort(value);
        } else if (targetType == byte.class || targetType == Byte.class) {
            return Byte.parseByte(value);
        } else if (targetType == char.class || targetType == Character.class) {
            return value.charAt(0);
        } else if (targetType == java.sql.Date.class) {
            return java.sql.Date.valueOf(value);
        }
        throw new IllegalArgumentException("Unsupported field type: " + targetType);
    }

}
