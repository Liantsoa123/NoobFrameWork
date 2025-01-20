package mg.noobframework.auth;

import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;
import mg.noobframework.annotation.AuthMethod;

public class AuthMethodUtils {
    private String userName;
    private String roles;

    public AuthMethodUtils(String userName, String roles) {
        this.userName = userName;
        this.setRoles(roles);
    }

    public AuthMethodUtils() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isAuthenticated(HttpServletRequest request, Method method) {
        String roleUser = (String) request.getSession().getAttribute(roles);

        boolean isAuth = false;
        if (method.isAnnotationPresent(AuthMethod.class)) {
            AuthMethod auth = method.getAnnotation(AuthMethod.class);
            String[] roles = auth.value();
            for (String role : roles) {
                if (role.equals(roleUser)) {
                    isAuth = true;
                    break;
                }
            }
        } else {
            isAuth = true;
        }
        return isAuth;
    }
}