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
    public static void checkValidation(Field field, String paramValue, HttpServletRequest request) throws Exception {
        HashMap<String, String> error = new HashMap<>();
        if (paramValue == null || paramValue.isEmpty()) {
            if (field.isAnnotationPresent(Required.class)) {
                // throw new Exception("value required " + field.getName());
                error.put(field.getName(), "value required");
            }
        } else {
            if (field.isAnnotationPresent(Numerique.class)) {
                if (!StringUtils.isNumeric(paramValue)) {
                    // throw new Exception("value must be numeric " + field.getName());
                    error.put(field.getName(), "value must be numeric");
                }
            } else if (field.isAnnotationPresent(Date.class)) {
                if (!StringUtils.isDate(paramValue)) {
                    // throw new Exception("value must be date " + field.getName());
                    error.put(field.getName(), "value must be date");

                }
            } else if (field.isAnnotationPresent(Email.class)) {
                if (!StringUtils.isEmail(paramValue)) {
                    // throw new Exception("value must be email " + field.getName());
                    error.put(field.getName(), "value must be email");
                }
            }
        }
        if (!error.isEmpty()) {
            request.setAttribute("error", error);
        }
    }

}
