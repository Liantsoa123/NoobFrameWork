package mg.noobframework.file;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileUtils {
    public static byte[] convertPartToBytes(Part part) throws IOException {
        try (InputStream inputStream = part.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        }
    }

    public static String extractFileName(Part part) {
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
