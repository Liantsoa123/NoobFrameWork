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
import mg.noobframework.annotation.RequestParamObject;
import mg.noobframework.modelview.Modelview;
import mg.noobframework.session.Mysession;

public class MethodUtils {
    public static List<Object> getParamValue(Method method, HttpServletRequest request) throws Exception {
        List<Object> listValue = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        String paramName;
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(RequestParam.class)) {
                paramName = parameter.getAnnotation(RequestParam.class).value();
                listValue.add(request.getParameter(paramName));
            } else {
                // listValue.add("");
                throw new Exception(
                        "ETU002510  there is no annotation in the parameter of the function You want to use ");
            }
        }
        return listValue;
    }

    public static List<Object> getParamValue(Mapping mapping, HttpServletRequest request)
            throws Exception {
        List<Object> listObjects = new ArrayList<>();
        Parameter[] parameters = mapping.getMethodMapping().getParameters();
        for (Parameter parameter : parameters) {
            Object obj = new Object();
            if (parameter.isAnnotationPresent(RequestParamObject.class)) {
                obj = ObjectUtils.doSetter(parameter.getType(), request);
            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                obj = request.getParameter(parameter.getAnnotation(RequestParam.class).value());
            } else if (parameter.getClass().equals(Mysession.class)) {
                obj = new Mysession(request.getSession());
            } else {
                throw new Exception(
                        "ETU002510  there is no annotation in the parameter of the function You want to use ");
            }
            listObjects.add(obj);
        }
        return listObjects;
    }

    public static Object executMethod(Mapping mapping, HttpServletRequest request) throws Exception {
        Class<?> clazz = mapping.getClazzMapping();
        Object obj = clazz.getConstructor().newInstance();
        Method method = mapping.getMethodMapping();
        List<Object> paramValue = getParamValue(mapping, request);
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
