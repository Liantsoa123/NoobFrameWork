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

    public String isAuthenticated(HttpServletRequest request, Mapping mapping, String verb) throws Exception {
        String roleUser = (String) request.getSession().getAttribute("roles");

        // Check auth method
        if (mapping.getMethodMapping(verb).isAnnotationPresent(AuthMethod.class)) {
            AuthMethod authMethod = mapping.getMethodMapping(verb).getAnnotation(AuthMethod.class);
            String[] rolesMethod = authMethod.value();

            for (String role : rolesMethod) {
                if (role.equalsIgnoreCase(roleUser)) {
                    return null;
                }
            }
            return "Access Denied: Insufficient permissions. " +
                    "Your current role (" + roleUser + ") does not have access to this resource. " +
                    "Required roles: " + String.join(", ", rolesMethod) + ". " +
                    "Please contact your system administrator if you believe this is an error.";
        }
        // Check auth Class
        else if (mapping.getClazzMapping().isAnnotationPresent(AuthClass.class)) {
            AuthClass authClass = mapping.getClazzMapping().getAnnotation(AuthClass.class);
            String[] roles = authClass.value();

            for (String role : roles) {
                if (role.equalsIgnoreCase(roleUser)) {
                    return null;
                }
            }
            return "Access Denied: Insufficient permissions. " +
                    "Your current role (" + roleUser + ") does not have access to this section. " +
                    "Required roles: " + String.join(", ", roles) + ". " +
                    "Please contact your system administrator if you believe this is an error.";
        }

        return null;
    }
}