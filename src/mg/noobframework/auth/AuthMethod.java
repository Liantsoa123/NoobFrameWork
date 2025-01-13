package mg.noobframework.auth;

import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.Auth;

public class AuthMethod {
    private String userName;
    private String roles;

    public AuthMethod(String userName, String roles) {
        this.userName = userName;
        this.setRoles(roles);
    }

    public AuthMethod() {
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

    public boolean isAuth(HttpServletRequest request, Method method) {
        String role_user = (String) request.getSession().getAttribute(roles);
        boolean isAuth = false;
        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth = method.getAnnotation(Auth.class);
            String[] role = auth.value().split(",");
            for (String r : role) {
                if (role_user.equals(r)) {
                    isAuth = true;
                    break;
                }
            }
        }
        return isAuth;
    }
}