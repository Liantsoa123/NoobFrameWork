package mg.noobframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.Controller;
import mg.noobframework.utils.ClassFinder;
import mg.noobframework.utils.Mapping;
import mg.noobframework.utils.MethodUtils;
import mg.noobframework.utils.ObjectUtils;
import mg.noobframework.utils.StringUtils;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> listeMethodes;
    private Exception exception;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        if (exception != null) {
            // out.println("<p>" + exception.getMessage() + "</p>");
            // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            // exception.getMessage());
            exception.printStackTrace(out);
            return;
        }
        try {
            String url = req.getRequestURI().replace("/NoobFrameWork", "");
            out.println("<h1>Noob_FrameWork</h1>");
            out.println("<p>Votre url = " + url + "</p>");
            if (listeMethodes.get(url) != null) {
                out.println("<p>Class= " + listeMethodes.get(url).getClazzMapping().getName() + "</p>");
                out.println("<p>Method= " + listeMethodes.get(url).getMethodMapping().getName() + "</p>");

                MethodUtils.doMethod(req, resp, listeMethodes.get(url), out);

            } else {

                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Url not Found");
            }
        } catch (Exception e) {
            // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            e.printStackTrace(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void init() throws ServletException {
        String packageName = this.getInitParameter("controller_dir");
        if (packageName == null) {
            exception = new Exception("controller_dir is null");
            return;
        }
        try {
            ArrayList<Class<?>> controller = ClassFinder.getAllClassAnnotation(packageName, Controller.class);
            listeMethodes = ClassFinder.getMethod(controller);

        } catch (Exception e) {
            exception = e;
        }
    }

}