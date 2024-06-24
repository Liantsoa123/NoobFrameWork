package mg.noobframework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;

public class ObjectUtils {
    public static Object doSetter(Class<?> clazz, HttpServletRequest request) throws Exception {
        Object obj = clazz.getConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String paramName = field.getName();
            String paramValue = request.getParameter(paramName);

            if (paramValue != null) {
                String setterName = "set" + StringUtils.toUppurcaseFirstLetter(field.getName());
                Method setterMethod = clazz.getDeclaredMethod(setterName, field.getType());

                Object convertedValue = convertValue(paramValue, field.getType());

                setterMethod.invoke(obj, convertedValue);
            }
        }

        return obj;
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
        }
        throw new IllegalArgumentException("Unsupported field type: " + targetType);
    }

}
