package mg.noobframework.auth;

public class AuthMethod {
    private String userName;
    private String[] roles;

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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles.split(",");
    }

}
