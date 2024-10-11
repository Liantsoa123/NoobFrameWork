package mg.noobframework.error;

import jakarta.servlet.http.HttpServlet;
import java.io.PrintWriter;

public class ErrorHandler extends HttpServlet {

    public static void printError(PrintWriter out, Exception e) {

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>NOOBFRAMEWORK Error</title>");
        out.println("<style>");
        out.println(
                "body {font-family: Arial, sans-serif; background-color: #f8f9fa; color: #343a40; text-align: center; padding: 50px;}");
        out.println(
                ".error-container {background-color: white; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); margin: 0 auto; padding: 30px;}");
        out.println("h1 {font-size: 48px; color: #dc3545; margin-bottom: 10px;}");
        out.println("p {font-size: 18px; margin: 10px 0;}");
        out.println(".error {text-align: left; background-color: #f0d0d0; padding: 1rem; border-radius: 10px;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='error-container'>");
        out.println("<h1>Oops!</h1>");
        out.println("<p>Something went wrong.</p>");
        out.println("<p style='color: #dc3545;'>Error Message:</p>");
        out.println("<p class='error'>");
        e.printStackTrace(out);
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
