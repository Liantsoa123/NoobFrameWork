package mg.noobframework.url;

import java.lang.reflect.Method;

public class VerbAction {
    private String verb;
    private Method action;

    public VerbAction(String verb, Method action) {
        this.setVerb(verb);
        this.setAction(action);
    }

    public VerbAction() {
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VerbAction) {
            VerbAction verbAction = (VerbAction) obj;
            // return verbAction.getVerb().equals(this.getVerb());
            if (verbAction.getVerb().equals(this.getVerb())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
