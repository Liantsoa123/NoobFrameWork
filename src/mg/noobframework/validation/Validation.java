package mg.noobframework.validation;

import java.lang.reflect.Field;

import mg.noobframework.annotation.Date;
import mg.noobframework.annotation.Email;
import mg.noobframework.annotation.Numerique;
import mg.noobframework.annotation.Required;
import mg.noobframework.utils.StringUtils;

public class Validation {
    public static void checkValidation(Field field, String paramValue) throws Exception {
        if (paramValue == null || paramValue.isEmpty()) {
            if (field.isAnnotationPresent(Required.class)) {
                throw new Exception("value required " + field.getName());
            }
        } else {
            if (field.isAnnotationPresent(Numerique.class)) {
                if (!StringUtils.isNumeric(paramValue)) {
                    throw new Exception("value must be numeric " + field.getName());
                }
            } else if (field.isAnnotationPresent(Date.class)) {
                if (!StringUtils.isDate(paramValue)) {
                    throw new Exception("value must be date " + field.getName());
                }
            } else if (field.isAnnotationPresent(Email.class)) {
                if (!StringUtils.isEmail(paramValue)) {
                    throw new Exception("value must be email " + field.getName());
                }
            }
        }
    }

}
