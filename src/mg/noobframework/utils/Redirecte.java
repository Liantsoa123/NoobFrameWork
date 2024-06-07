package mg.noobframework.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Redirecte {
    public static void redirecting(HttpServletRequest request, HttpServletResponse response, String url)
            throws Exception {
        RequestDispatcher rDispatcher = request.getRequestDispatcher(url);
        rDispatcher.forward(request, response);
    }
}
