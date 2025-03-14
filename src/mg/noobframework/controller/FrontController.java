package mg.noobframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.Controller;
import mg.noobframework.auth.AuthUtils;
import mg.noobframework.error.ErrorHandler;
import mg.noobframework.url.Mapping;
import mg.noobframework.utils.ClassFinder;
import mg.noobframework.utils.MethodUtils;

@MultipartConfig

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> listeMethodes;
    private Exception exception;
    private AuthUtils authMethodUtils;
    private String projectName;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp, String verb)
            throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        if (exception != null) {
            ErrorHandler.printError(out, exception, 500);
            return;
        }
        try {
            String url = req.getRequestURI().replace("/" + projectName, "");
            // Check if the request is for a static resource
            if (url.matches(".*\\.(css|js|jpg|jpeg|png|gif)$")) {
                // Forward to the default servlet
                getServletContext().getNamedDispatcher("default").forward(req, resp);
                return;
            }
            if (listeMethodes.get(url) != null) {
                MethodUtils.doMethod(req, resp, listeMethodes.get(url), out, verb, authMethodUtils);
            } else {
                ErrorHandler.printError(out, new Exception("Url not found : " + url), 404);
                return;
            }
        } catch (Exception e) {
            ErrorHandler.printError(out, e, 500);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String verb = "GET";
        processRequest(req, resp, verb);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String verb = "POST";
        processRequest(req, resp, verb);
    }

    @Override
    public void init() throws ServletException {
        // Get about project name
        projectName = this.getInitParameter("project_name");
        if (projectName == null) {
            exception = new Exception("project_name is null");
            return;
        }

        // Get about controller dir
        String packageName = this.getInitParameter("controller_dir");
        if (packageName == null) {
            exception = new Exception("controller_dir is null");
            return;
        }

        // Get about auth
        String user_session_name = getInitParameter("user_name");
        String roles = getInitParameter("roles_name");
        if (!user_session_name.equals(null) && !roles.equals(null)) {
            authMethodUtils = new AuthUtils(user_session_name, roles);
        }

        try {
            ArrayList<Class<?>> controller = ClassFinder.getAllClassAnnotation(packageName, Controller.class);
            listeMethodes = ClassFinder.getMethod(controller);

        } catch (Exception e) {
            exception = e;
        }
    }

}