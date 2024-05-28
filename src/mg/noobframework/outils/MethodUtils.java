package mg.noobframework.outils;

import java.lang.reflect.Method;

public class MethodUtils {
    public Object executMethod(Mapping mapping) throws Exception {
        Class<?> clazz = Class.forName(mapping.getClassName());
        Object obj = clazz.getConstructor().newInstance();
        Method method = clazz.getMethod(mapping.getMethodName());
        return method.invoke(obj);
    }
}
