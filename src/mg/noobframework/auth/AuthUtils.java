package mg.noobframework.auth;

import jakarta.servlet.http.HttpServletRequest;
import mg.noobframework.annotation.AuthClass;
import mg.noobframework.annotation.AuthMethod;
import mg.noobframework.url.Mapping;

public class AuthUtils {
    private String userName;
    private String roles;

    public AuthUtils(String userName, String roles) {
        this.userName = userName;
        this.setRoles(roles);
    }

    public AuthUtils() {
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

    public boolean isAuthenticated(HttpServletRequest request, Mapping mapping, String verb) throws Exception {
        String roleUser = (String) request.getSession().getAttribute(roles);

        boolean isAuth = false;
        // check auth method
        if (mapping.getMethodMapping(verb).isAnnotationPresent(AuthMethod.class)) {
            AuthMethod authMethod = mapping.getMethodMapping(verb).getAnnotation(AuthMethod.class);
            String[] rolesMethod = authMethod.value();
            for (String role : rolesMethod) {
                if (role.equals(roleUser)) {
                    isAuth = true;
                    break;
                }
            }
        }
        // check auth Class
        else if (mapping.getClazzMapping().isAnnotationPresent(AuthClass.class)) {
            AuthClass authClass = mapping.getClazzMapping().getAnnotation(AuthClass.class);
            String[] roles = authClass.value();
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