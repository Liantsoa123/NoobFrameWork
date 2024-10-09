package mg.noobframework.url;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class Mapping {
    public Mapping() {
    }

    private Class<?> clazzMapping;
    private List<VerbAction> verbAction = new ArrayList<>();

    public Mapping(Class<?> clazzMapping, List<VerbAction> verbAction) {
        this.setClazzMapping(clazzMapping);
        this.setVerbAction(verbAction);
    }

    public Class<?> getClazzMapping() {
        return clazzMapping;
    }

    public void setClazzMapping(Class<?> clazzMapping) {
        this.clazzMapping = clazzMapping;
    }

    public List<VerbAction> getVerbAction() {
        return verbAction;
    }

    public void setVerbAction(List<VerbAction> verbAction) {
        this.verbAction = verbAction;
    }

    public Method getMethodMapping(String verb) throws Exception {
        Method action = null;
        for (VerbAction verbAction : verbAction) {
            if (verbAction.getVerb().equals(verb)) {
                action = verbAction.getAction();
            }
        }
        if (action == null) {
            throw new Exception("No action found for the method " + verb + " with the class " + clazzMapping.getName());
        } else {
            return action;
        }
    }

    public boolean is_already_exist(String verb) {
        for (VerbAction verbAction : verbAction) {
            if (verbAction.getVerb().equalsIgnoreCase(verb)) {
                return true;
            }
        }
        return false;
    }
}
