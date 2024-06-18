package mg.noobframework.utils;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.RequestParam;
import mg.noobframework.modelview.Modelview;

public class MethodUtils {
    public static List<Object> getParamValue(Method method, HttpServletRequest request) {
        List<Object> listValue = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        String paramName;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(RequestParam.class)) {
                paramName = parameter.getAnnotation(RequestParam.class).value();
                listValue.add(request.getParameter(paramName));
            } else {
                listValue.add("");
            }
        }
        return listValue;
    }

    public static Object executMethod(Mapping mapping, HttpServletRequest request) throws Exception {
        Class<?> clazz = Class.forName(mapping.getClassName());
        Object obj = clazz.getConstructor().newInstance();
        Method method = clazz.getMethod(mapping.getMethodName());
        List<Object> paramValue = getParamValue(method, request);
        return method.invoke(obj, paramValue.toArray());
    }

    public static void doMethod(HttpServletRequest request, HttpServletResponse response, Mapping mapping,
            PrintWriter pWriter) throws Exception {
        Object result = executMethod(mapping, request);
        if (result instanceof String) {
            pWriter.println("<p>" + result + "</p>");
        } else if (result instanceof Modelview) {
            Modelview modelview = (Modelview) result;
            HashMap<String, Object> data = modelview.getData();
            for (String key : data.keySet()) {
                request.setAttribute(key, data.get(key));
            }
            Redirecte.redirecting(request, response, modelview.getUrl());

        } else {
            throw new Exception("unknown value");
        }
    }
}
