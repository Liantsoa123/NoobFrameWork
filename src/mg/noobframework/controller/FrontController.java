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
import mg.noobframework.utils.ClassFinder;
import mg.noobframework.utils.Mapping;
import mg.noobframework.utils.MethodUtils;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> listeMethodes;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        try {
            String url = req.getRequestURI().replace("/NoobFrameWork", "");
            out.println("<h1>Noob_FrameWork</h1>");
            out.println("<p>Votre url = " + url + "</p>");
            if (listeMethodes.get(url) != null) {
                out.println("<p>Class= " + listeMethodes.get(url).getClassName() + "</p>");
                out.println("<p>Method= " + listeMethodes.get(url).getMethodName() + "</p>");
                out.println("<p>ExecutMethod= " + MethodUtils.executMethod(listeMethodes.get(url)));
            } else {
                out.println("There is no method associated with this url");
            }
        } catch (Exception e) {
            out.println(e.getMessage());
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