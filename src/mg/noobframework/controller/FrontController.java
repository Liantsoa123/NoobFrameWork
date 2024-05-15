package mg.noobframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.noobframework.outils.ClassFinder;

public class FrontController extends HttpServlet {
    private ArrayList<Class<?>> listController;

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        out.println("<h1>Noob_FrameWork</h1>");
        out.println("<p>Votre url = " + req.getRequestURI() + "</p>");
        out.println("huhu");
        try {
            String packageName = this.getInitParameter("package-controller");
            out.println("packagesName"+packageName);
            listController = ClassFinder.getController(packageName);

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

    // @Override
    // public void init() throws ServletException {
    //     try {
    //         String packageName = this.getInitParameter("package-controller");
    //         listController = ClassFinder.getController(packageName);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

}