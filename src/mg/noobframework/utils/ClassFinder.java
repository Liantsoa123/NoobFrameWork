package mg.noobframework.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import mg.noobframework.annotation.Post;
import mg.noobframework.annotation.Url;
import mg.noobframework.url.Mapping;
import mg.noobframework.url.VerbAction;

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

    public static HashMap<String, Mapping> getMethod(ArrayList<Class<?>> controller) throws Exception {
        HashMap<String, Mapping> values = new HashMap<String, Mapping>();
        for (Class<?> class1 : controller) {
            Method[] methods = class1.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Url.class)) {
                    String url = method.getAnnotation(Url.class).value();
                    Mapping mapping = new Mapping();
                    VerbAction verbAction = new VerbAction();
                    verbAction.setAction(method);
                    verbAction.setVerb("GET");
                    
                    if (method.isAnnotationPresent(Post.class)) {
                        verbAction.setVerb("POST");
                    }

                    // Check if url already exist
                    if (values.get(url) != null) {
                        if (!values.get(url).getVerbAction().add(verbAction)) {
                            throw new Exception("ETU002510: The verb '" + verbAction.getVerb()
                                    + "' already exist for the url '" + url + "'");
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
