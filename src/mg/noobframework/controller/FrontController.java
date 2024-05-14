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
        for (Class<?> class1 : listController) {
            out.println("<p>" + class1.getSimpleName() + "</p>");
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
        try {
            String path = getServletContext().getRealPath("WEB-INF\\classes");
            listController = ClassFinder.getController(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}