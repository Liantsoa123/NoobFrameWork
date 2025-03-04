package mg.noobframework.utils;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mg.noobframework.annotation.RequestParam;
import mg.noobframework.annotation.RequestParamObject;
import mg.noobframework.annotation.RestApi;
import mg.noobframework.auth.AuthUtils;
import mg.noobframework.file.File;
import mg.noobframework.file.FileUtils;
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
                throw new Exception(
                        "ETU002510  annotation not found ");
            }
        }
        return listValue;
    }

    public static List<Object> getParamValues(Mapping mapping, HttpServletRequest request, String verb,
            HashMap<String, String> error) throws Exception {
        List<Object> listObject = new ArrayList<>();
        Parameter[] parameters = mapping.getMethodMapping(verb).getParameters();

        for (Parameter parameter : parameters) {
            Object obj = null;
            try {
                // check if it's File Class
                if (File.class.equals(parameter.getType())) {
                    RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                    if (requestParam != null) {
                        File file = new File();
                        Part part = request.getPart(requestParam.value());
                        file.setByteFromPart(part);
                        file.setFileName(FileUtils.extractFileName(part));
                        obj = file;
                    }
                }
                // check session
                else if (parameter.getType().equals(Mysession.class)) {
                    obj = new Mysession(request.getSession());
                } else if (parameter.isAnnotationPresent(RequestParamObject.class)) {
                    obj = ObjectUtils.doSetter(parameter.getType(), request, error);
                } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                    String paramValue = request.getParameter(parameter.getAnnotation(RequestParam.class).value());
                    obj = ObjectUtils.convertValue(paramValue, parameter.getType());
                } else {
                    throw new Exception("ETU002510: No valid annotation found for parameter " + parameter.getName());
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Erreur de conversion pour le paramètre " + parameter.getName() +
                        ": " + e.getMessage());
            }
            listObject.add(obj);
        }
        return listObject;
    }

    public static Object executMethod(Mapping mapping, HttpServletRequest request, String verb,
            HashMap<String, String> error) throws Exception {
        Class<?> clazz = mapping.getClazzMapping();
        Object obj = clazz.getConstructor().newInstance();
        setSessionAttribut(obj, request);
        Method method = mapping.getMethodMapping(verb);
        List<Object> paramValue = getParamValues(mapping, request, verb, error);

        return method.invoke(obj, paramValue.toArray());
    }

    public static void doMethod(HttpServletRequest request, HttpServletResponse response, Mapping mapping,
            PrintWriter pWriter, String verb, AuthUtils authMethod) throws Exception {
        HashMap<String, String> error = new HashMap<>();

        if (authMethod != null) {
            // CHECK AUTHENTICATION
            String authError = authMethod.isAuthenticated(request, mapping, verb);
            if (authError != null) {
                throw new Exception(authError);
            }
        }

        // CHECK VALIDATION
        List<Object> paramValues = getParamValues(mapping, request, verb, error);
        if (error.size() > 0) {
            String url = request.getParameter("url");
            if (url == null) {
                url = request.getHeader("Referer");
                if (url != null) {
                    url = url.substring(url.indexOf(request.getContextPath()) + request.getContextPath().length());
                }
            }

            HttpServletRequest getRequest = new HttpServletRequestWrapper(request) {
                @Override
                public String getMethod() {
                    return "GET";
                }
            };
            request.setAttribute("error", error);
            Redirecte.redirecting(getRequest, response, url);
            return;
        }

        Object result = executMethod(mapping, request, verb, error);

        if (result instanceof String) {
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
