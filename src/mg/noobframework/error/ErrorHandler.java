package mg.noobframework.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorHandler extends HttpServlet {

    public void printError(PrintWriter out, Exception e) {

        // Writing the error message with CSS styling to the response
        out.getWriter().write("<html><head><title>NOOBFRAMEWORK Error</title>");

        // CSS Styles
        out.getWriter().write("<style>");
        out.getWriter().write(
                "body { font-family: Arial, sans-serif; background-color: #f8f9fa; color: #343a40; text-align: center; padding: 50px; }");
        out.getWriter().write(
                ".error-container { background-color: white; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto; padding: 30px; }");
        out.getWriter().write("h1 { font-size: 48px; color: #dc3545; margin-bottom: 10px; }");
        out.getWriter().write("p { font-size: 18px; margin: 10px 0; }");
        out.getWriter().write("a { color: #007bff; text-decoration: none; font-weight: bold; }");
        out.getWriter().write("a:hover { text-decoration: underline; }");
        out.getWriter().write("</style>");

        out.getWriter().write("</head><body>");

        // Content
        out.getWriter().write("<div class='error-container'>");
        out.getWriter().write("<h1>Oops!</h1>");
        out.getWriter().write("<p>Something went wrong.</p>");

        // Error Message
        out.getWriter().write("<p>Error Message: " + e.printStackTrace(out) + "</p>");

        out.getWriter().write("<p><a href='/'>Return to Home</a></p>");
        out.getWriter().write("</div>");

        out.getWriter().write("</body></html>");
    }
}
