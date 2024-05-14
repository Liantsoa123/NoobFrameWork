package mg.noobframework.outils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClassFinder {
    public static ArrayList<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        File packageDir = new File(classLoader.getResource(path).getFile().replace("%20", " "));

        for (File file : packageDir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(getClasses(packageName + "." + file.getName()));
            } else {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

    public static ArrayList<Class<?>> getController(String packageName) throws Exception {
        ArrayList<Class<?>> listController = new ArrayList<Class<?>>();
        


        return listController;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Class<?>> classes = getClasses("mg");
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getSimpleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
