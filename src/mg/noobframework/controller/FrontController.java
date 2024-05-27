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
    private HashMap < String , Mapping > listeMethodes ;
    

    

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        out.println("<h1>Noob_FrameWork</h1>");
        out.println("<p>Votre url = " + req.getRequestURI() + "</p>");

        
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