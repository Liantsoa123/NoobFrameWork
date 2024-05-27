package mg.noobframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.Controller;
import mg.noobframework.outils.ClassFinder;
import mg.noobframework.outils.Mapping;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> listeMethodes;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        String url = req.getRequestURI().replace("/NoobFramwork", "");
        out.println("<h1>Noob_FrameWork</h1>");
        out.println("<p>Votre url = " + url + "</p>");
        if (listeMethodes.get(url) != null) {
            out.println("<p>Class= " + listeMethodes.get(url).getClassName() + "</p>");
            out.println("<p>Method= " + listeMethodes.get(url).getMethodName() + "</p>");
        } else {
            out.println("There is no method associated with this path");
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
        try {
            ArrayList<Class<?>> controller = ClassFinder.getAllClassAnnotation(packageName, Controller.class);
            listeMethodes = ClassFinder.getMethod(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}