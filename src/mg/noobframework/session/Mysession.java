package mg.noobframework.session;

import jakarta.servlet.http.HttpSession;

public class Mysession {
    HttpSession httpSession;

    public Mysession(HttpSession httpSession) {
        this.setHttpSession(httpSession);
    }

    public Mysession() {
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Object get(String key) {
        return getHttpSession().getAttribute(key);
    }

    public void add(String key, Object object) {
        getHttpSession().setAttribute(key, object);
    }

    public void delete(String key) {
        getHttpSession().removeAttribute(key);
    }

}
