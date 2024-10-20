package mg.noobframework.utils;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mg.noobframework.annotation.RequestParam;
import mg.noobframework.annotation.RequestParamObject;
import mg.noobframework.annotation.RestApi;
import mg.noobframework.modelview.Modelview;
import mg.noobframework.session.Mysession;
import mg.noobframework.url.Mapping;

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
                        "ETU002510  annotation not found ");
            }
        }
        return listValue;
    }

    public static List<Object> getParamValues(Mapping mapping, HttpServletRequest request, String verb)
            throws Exception {
        List<Object> listObject = new ArrayList<>();
        Parameter[] parameters = mapping.getMethodMapping(verb).getParameters();

        for (Parameter parameter : parameters) {
            Object obj = null;
            // check if it's Part
            // Check if it's Part type
            if (Part.class.isAssignableFrom(parameter.getType())) {
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                if (requestParam != null) {
                    obj = request.getPart(requestParam.value());
                }
            }
            // check session
            else if (parameter.getType().equals(Mysession.class)) {
                obj = new Mysession(request.getSession());
            } else if (parameter.isAnnotationPresent(RequestParamObject.class)) {
                obj = ObjectUtils.doSetter(parameter.getType(), request);
            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                obj = request.getParameter(parameter.getAnnotation(RequestParam.class).value());
            } else {
                throw new Exception("ETU002510: No valid annotation found for the parameter '"
                        + parameter.getName() + "' of type '" + parameter.getType()
                        + "' in the function you want to use");
            }
            listObject.add(obj);
        }
        return listObject;

    }

    public static Object executMethod(Mapping mapping, HttpServletRequest request, String verb) throws Exception {
        Class<?> clazz = mapping.getClazzMapping();
        Object obj = clazz.getConstructor().newInstance();
        setSessionAttribut(obj, request);
        Method method = mapping.getMethodMapping(verb);
        List<Object> paramValue = getParamValues(mapping, request, verb);
        return method.invoke(obj, paramValue.toArray());
    }

    public static void doMethod(HttpServletRequest request, HttpServletResponse response, Mapping mapping,
            PrintWriter pWriter, String verb) throws Exception {
        Object result = executMethod(mapping, request, verb);
        if (result instanceof String) {
            // pWriter.println("<p>" + result + "</p>");
            pWriter.println(new Gson().toJson(result));
        } else if (result instanceof Modelview) {
            Modelview modelview = (Modelview) result;
            HashMap<String, Object> data = modelview.getData();
            if (mapping.getMethodMapping(verb).isAnnotationPresent(RestApi.class)) {
                pWriter.println(new Gson().toJson(data));
            } else {
                for (String key : data.keySet()) {
                    request.setAttribute(key, data.get(key));
                }
                Redirecte.redirecting(request, response, modelview.getUrl());
            }
        } else {
            throw new Exception("unknown value");
        }
    }

    public static void setSessionAttribut(Object object, HttpServletRequest request) throws Exception {

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(Mysession.class)) {
                Method seMethod = ObjectUtils.getSetterMethod(object.getClass(), field);
                seMethod.invoke(object, new Mysession(request.getSession()));
            }
        }
    }
}
