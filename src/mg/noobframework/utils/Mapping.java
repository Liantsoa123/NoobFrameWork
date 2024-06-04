package mg.noobframework.utils;

public class Mapping {
    public Mapping() {
    }

    public Mapping(String className, String methodName) {
        this.setClassName(className);
        this.setMethodName(methodName);
    }

    private String className, methodName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
