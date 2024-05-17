package mg.noobframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.annotation.Controller;
import mg.noobframework.outils.ClassFinder;

public class FrontController extends HttpServlet {
    private ArrayList<Class<?>> listController;
    private boolean ischecked = false;

    private void initvariable(PrintWriter out) {
        try {
            String packageName = this.getInitParameter("controller_dir");
            listController = ClassFinder.getAllClassAnnotation(packageName, Controller.class);
        } catch (Exception e) {
            if (out != null) {
                out.println("<p>" + e.getMessage() + "</p>");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        out.println("<h1>Noob_FrameWork</h1>");
        out.println("<p>Votre url = " + req.getRequestURI() + "</p>");

        if (!ischecked) {
            initvariable(out);
            ischecked = true;
        }
        try {
            out.println("<h3>Listes Controller</h3>");
            for (Class<?> clas : listController) {
                out.println("<li>" + clas.getSimpleName() + "</li>");
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
        initvariable(null);
        ischecked = true;
    }

}