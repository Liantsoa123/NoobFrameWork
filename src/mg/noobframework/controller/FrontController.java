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
import mg.noobframework.error.ErrorHandler;
import mg.noobframework.url.Mapping;
import mg.noobframework.utils.ClassFinder;
import mg.noobframework.utils.MethodUtils;

@MultipartConfig

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> listeMethodes;
    private Exception exception;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp, String verb)
            throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        if (exception != null) {
            ErrorHandler.printError(out, exception);
            return;
        }
        try {
            String url = req.getRequestURI().replace("/NoobFrameWork", "");
            if (listeMethodes.get(url) != null) {
                MethodUtils.doMethod(req, resp, listeMethodes.get(url), out, verb);
            } else {
                // resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Url not Found");
                ErrorHandler.printError(out, new Exception("Url not found : " + url));
                return;
            }
        } catch (Exception e) {
            // resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            ErrorHandler.printError(out, e);
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