package mg.noobframework.error;

import jakarta.servlet.http.HttpServlet;
import java.io.PrintWriter;

public class ErrorHandler extends HttpServlet {

    public static void printError(PrintWriter out, Exception e) {

        // Writing the error message with CSS styling to the response
        out.write("<html><head><title>NOOBFRAMEWORK Error</title>");

        // CSS Styles
        out.write("<style>");
        out.write(
                "body { font-family: Arial, sans-serif; background-color: #f8f9fa; color: #343a40; text-align: center; padding: 50px; }");
        out.write(
                ".error-container { background-color: white; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); max-width: 600px; margin: 0 auto; padding: 30px; }");
        out.write("h1 { font-size: 48px; color: #dc3545; margin-bottom: 10px; }");
        out.write("p { font-size: 18px; margin: 10px 0; }");
        out.write("a { color: #007bff; text-decoration: none; font-weight: bold; }");
        out.write("a:hover { text-decoration: underline; }");
        out.write("</style>");

        out.write("</head><body>");

        // Content
        out.write("<div class='error-container'>");
        out.write("<h1>Oops!</h1>");
        out.write("<p>Something went wrong.</p>");

        // Error Message
        out.write("<p>Error Message: ");
        e.printStackTrace(out);
        out.print("</p>");

        out.write("</div>");

        out.write("</body></html>");
    }
}
