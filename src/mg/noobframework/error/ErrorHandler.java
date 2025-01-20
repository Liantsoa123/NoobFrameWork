package mg.noobframework.error;

import jakarta.servlet.http.HttpServlet;
import java.io.PrintWriter;

public class ErrorHandler extends HttpServlet {

    public static void printError(PrintWriter out, Exception e, int statusCode) {
        // Get a user-friendly message based on status code
        String statusMessage = getStatusMessage(statusCode);

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Error " + statusCode + " - NOOBFRAMEWORK</title>");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("""
                    body {
                        font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
                        background: linear-gradient(135deg, #f5f7fa 0%, #e4e8eb 100%);
                        color: #2d3748;
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 20px;
                        line-height: 1.6;
                    }
                """);
        out.println("""
                    .error-container {
                        background-color: white;
                        border-radius: 16px;
                        box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
                        width: 100%;
                        max-width: 800px;
                        padding: 2.5rem;
                        margin: 20px;
                        animation: slideIn 0.5s ease-out;
                    }
                """);
        out.println("""
                    @keyframes slideIn {
                        from {
                            transform: translateY(-20px);
                            opacity: 0;
                        }
                        to {
                            transform: translateY(0);
                            opacity: 1;
                        }
                    }
                """);
        out.println("""
                    .status-code {
                        font-size: 5rem;
                        font-weight: 700;
                        color: #e53e3e;
                        margin-bottom: 0.5rem;
                        letter-spacing: -0.025em;
                    }
                """);
        out.println("""
                    .status-message {
                        font-size: 1.5rem;
                        font-weight: 600;
                        color: #2d3748;
                        margin-bottom: 1.5rem;
                    }
                """);
        out.println("""
                    .error-message {
                        font-size: 1.125rem;
                        color: #718096;
                        margin-bottom: 2rem;
                        padding: 1rem;
                        background-color: #f7fafc;
                        border-radius: 8px;
                    }
                """);
        out.println("""
                    .stack-trace-label {
                        font-size: 1rem;
                        color: #4a5568;
                        font-weight: 600;
                        margin-bottom: 0.75rem;
                        display: flex;
                        align-items: center;
                        gap: 0.5rem;
                        cursor: pointer;
                    }
                """);
        out.println("""
                    .stack-trace-container {
                        background-color: #fff5f5;
                        border: 1px solid #fed7d7;
                        border-radius: 12px;
                        padding: 1.5rem;
                        margin-top: 1rem;
                        overflow-x: auto;
                        font-family: 'Consolas', monospace;
                        font-size: 0.875rem;
                        line-height: 1.7;
                        color: #4a5568;
                    }
                """);
        out.println("""
                    .stack-trace-container::-webkit-scrollbar {
                        width: 8px;
                        height: 8px;
                    }
                    .stack-trace-container::-webkit-scrollbar-track {
                        background: #f7fafc;
                        border-radius: 4px;
                    }
                    .stack-trace-container::-webkit-scrollbar-thumb {
                        background: #cbd5e0;
                        border-radius: 4px;
                    }
                    .stack-trace-container::-webkit-scrollbar-thumb:hover {
                        background: #a0aec0;
                    }
                """);
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='error-container'>");
        out.println("<div class='status-code'>" + statusCode + "</div>");
        out.println("<div class='status-message'>" + statusMessage + "</div>");
        out.println("<div class='error-message'>" + e.getMessage() + "</div>");
        out.println("<details>");
        out.println("<summary class='stack-trace-label'>Show stack trace</summary>");
        out.println("<div class='stack-trace-container'>");
        e.printStackTrace(out);
        out.println("</div>");
        out.println("</details>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private static String getStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Page Not Found";
            case 405 -> "Method Not Allowed";
            case 500 -> "Internal Server Error";
            case 501 -> "Not Implemented";
            case 502 -> "Bad Gateway";
            case 503 -> "Service Unavailable";
            case 504 -> "Gateway Timeout";
            default -> "Unknown Error";
        };
    }
}