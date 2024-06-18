package mg.noobframework.utils;

import java.lang.reflect.Method;

public class Mapping {
    public Mapping() {
    }

    private Class<?> clazzMapping;
    private Method methodMapping;

    public Mapping(Class<?> clazzMapping, Method methodMapping) {
        this.setClazzMapping(clazzMapping);
        this.setMethodMapping(methodMapping);
    }

    public Class<?> getClazzMapping() {
        return clazzMapping;
    }

    public void setClazzMapping(Class<?> clazzMapping) {
        this.clazzMapping = clazzMapping;
    }

    public Method getMethodMapping() {
        return methodMapping;
    }

    public void setMethodMapping(Method methodMapping) {
        this.methodMapping = methodMapping;
    }

}
