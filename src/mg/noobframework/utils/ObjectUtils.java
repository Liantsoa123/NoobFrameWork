package mg.noobframework.utils;

import java.lang.reflect.Field;

public class ObjectUtils {
    public static Object doSetter(Class clazz) throws Exception {
        Object obj = clazz.getConstructor().newInstance();
        Field [] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            
        }
        return obj;
    }

}
