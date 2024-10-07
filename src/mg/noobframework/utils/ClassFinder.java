package mg.noobframework.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import mg.noobframework.annotation.Get;
import mg.noobframework.annotation.Post;
import mg.noobframework.annotation.Url;
import mg.noobframework.exception.UrlDuplicateException;

public class ClassFinder {
    public static ArrayList<Class<?>> getAllClassAnnotation(String packageName, Class<? extends Annotation> annotation)
            throws ClassNotFoundException, IOException, Exception {
        if (packageName.isBlank()) {
            throw new Exception("controller_dir is empty");
        }
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

    public static HashMap<String, Mapping> getMethod(ArrayList<Class<?>> controller) throws UrlDuplicateException {
        HashMap<String, Mapping> values = new HashMap<String, Mapping>();
        for (Class<?> class1 : controller) {
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Url.class)) {
                    String url = method.getAnnotation(Url.class).value();
                    Mapping mapping = new Mapping();
                    VerbAction verbAction = new VerbAction();
                    verbAction.setAction(method);

                    // Check verb
                    if (method.isAnnotationPresent(Get.class)) {
                        verbAction.setVerb("GET");
                    } else if (method.isAnnotationPresent(Post.class)) {
                        verbAction.setVerb("POST");
                    }

                    // Check if url already exist
                    if (values.get(url) != null && values.get(url).getClass().equals(class1)) {
                        if (values.get(url).getVerbAction().contains(verbAction)) {
                            throw new UrlDuplicateException("Url " + url + " already exist");
                        } else {
                            values.get(url).getVerbAction().add(verbAction);
                        }
                    } else {
                        mapping.setClazzMapping(class1);
                        mapping.getVerbAction().add(verbAction);
                        values.put(url, mapping);
                    }
                }
            }
        }
        return values;
    }
}
