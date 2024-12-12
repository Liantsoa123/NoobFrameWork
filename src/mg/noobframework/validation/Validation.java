package mg.noobframework.validation;

import java.lang.reflect.Field;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import mg.noobframework.annotation.Date;
import mg.noobframework.annotation.Email;
import mg.noobframework.annotation.Numerique;
import mg.noobframework.annotation.Required;
import mg.noobframework.utils.StringUtils;

public class Validation {
    public static boolean checkValidation(Field field, String paramValue, HttpServletRequest request,
            HashMap<String, String> error) throws Exception {
        if (paramValue == null || paramValue.isEmpty() || paramValue.isBlank()) {
            if (field.isAnnotationPresent(Required.class)) {
                error.put(field.getName(), "value required");
                return false;
            }
        } else {
            if (field.isAnnotationPresent(Numerique.class)) {
                if (!StringUtils.isNumeric(paramValue)) {
                    error.put(field.getName(), "value must be numeric");
                    return false;
                }
            } else if (field.isAnnotationPresent(Date.class)) {
                if (!StringUtils.isDate(paramValue)) {
                    error.put(field.getName(), "value must be date");
                    return false;
                }
            } else if (field.isAnnotationPresent(Email.class)) {
                if (!StringUtils.isEmail(paramValue)) {
                    error.put(field.getName(), "value must be email");
                    return false;
                }
            }
        }
        return true;
    }
}
