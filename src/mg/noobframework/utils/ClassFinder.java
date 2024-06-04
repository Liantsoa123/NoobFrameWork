package mg.noobframework.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import mg.noobframework.annotation.Get;

public class ClassFinder {
    public static ArrayList<Class<?>> getAllClassAnnotation(String packageName, Class<? extends Annotation> annotation)
            throws ClassNotFoundException, IOException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        File packageDir = new File(classLoader.getResource(path).getFile().replace("%20", " "));
        for (File file : packageDir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(getAllClassAnnotation(packageName + "." + file.getName(), annotation));
            } else {

                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> class1 = Class.forName(className);
                // check if it's controller
                if (class1.isAnnotationPresent(annotation)) {
                    classes.add(class1);
                }
            }
        }
        return classes;
    }

    public static HashMap<String, Mapping> getMethod(ArrayList<Class<?>> controller) {
        HashMap<String, Mapping> values = new HashMap<String, Mapping>();
        for (Class<?> class1 : controller) {
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Get.class)) {
                    String url = method.getAnnotation(Get.class).value();
                    values.put(url, new Mapping(class1.getName(), method.getName()));
                }
            }
        }
        return values;
    }
}
