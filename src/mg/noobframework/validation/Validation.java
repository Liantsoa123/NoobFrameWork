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
        // Vérifier d'abord si le champ est requis
        boolean isRequired = field.isAnnotationPresent(Required.class);

        if ((paramValue == null || paramValue.trim().isEmpty() || paramValue.isBlank())) {
            if (isRequired) {
                error.put(field.getName(), "value required");
                return false;
            }
        }

        // Validation des autres contraintes si la valeur n'est pas vide
        if (field.isAnnotationPresent(Numerique.class)) {
            if (!StringUtils.isNumeric(paramValue)) {
                error.put(field.getName(), "value must be numeric");
                return false;
            }

            Numerique num = field.getAnnotation(Numerique.class);
            double value = Double.parseDouble(paramValue);

            if (value < num.min()) {
                error.put(field.getName(), "value must be greater than " + num.min());
                return false;
            }
            if (value > num.max()) {
                error.put(field.getName(), "value must be less than " + num.max());
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
        return true;
    }
}
