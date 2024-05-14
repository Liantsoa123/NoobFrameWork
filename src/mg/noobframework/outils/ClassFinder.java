package mg.noobframework.outils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mg.noobframework.annotation.Controller;

public class ClassFinder {
    public static ArrayList<Class<?>> getController(String packageName) throws ClassNotFoundException, IOException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');

        File packageDir = new File(classLoader.getResource(path).getFile().replace("%20", " "));

        for (File file : packageDir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(getController(packageName + "." + file.getName()));
            } else {

                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> class1 = Class.forName(className);
                // check if it's controller
                if (class1.getDeclaredAnnotation(Controller.class) != null) {
                    classes.add(class1);
                }
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> list = getController("mg");

            for (Class<?> class1 : list) {
                System.out.println(class1.getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
