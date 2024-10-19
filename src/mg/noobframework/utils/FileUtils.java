package mg.noobframework.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static String uploadFile(Part filePart, String uploadDirectoryPath)
            throws IOException, ServletException {

        // Get the file name
        String fileName = extractFileName(filePart);

        // Create upload directory if it doesn't exist
        File uploadDirectory = new File(uploadDirectoryPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
        }
        // Save the file
        String filePath = uploadDirectoryPath + File.separator + fileName;
        filePart.write(filePath);

        return fileName; // Return the uploaded file name
    }

    // Helper method to extract the file name
    private static String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}
