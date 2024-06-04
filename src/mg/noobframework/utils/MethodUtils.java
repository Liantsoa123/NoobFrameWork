package mg.noobframework.utils;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.modelview.Modelview;

public class MethodUtils {
    public static Object executMethod(Mapping mapping) throws Exception {
        Class<?> clazz = Class.forName(mapping.getClassName());
        Object obj = clazz.getConstructor().newInstance();
        Method method = clazz.getMethod(mapping.getMethodName());
        return method.invoke(obj);
    }

    public static void doMethod(HttpServletRequest request, HttpServletResponse response, Mapping mapping,
            PrintWriter pWriter) throws Exception {
        Object result = executMethod(mapping);
        if (result instanceof String) {
            pWriter.println("<p>" + result + "</p>");
        } else if (result instanceof Modelview) {
            Modelview modelview = (Modelview) result;
            HashMap<String, Object> data = modelview.getData();
            for (String key : data.keySet()) {
                request.setAttribute(key, data.get(key));
            }
            RequestDispatcher rDispatcher = request.getRequestDispatcher(modelview.getUrl());
            rDispatcher.forward(request, response);

        } else {
            pWriter.println("unknown value");
        }
    }
}
