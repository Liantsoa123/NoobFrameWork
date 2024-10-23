package mg.noobframework.file;

import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.http.Part;
import java.nio.file.Path;
import java.nio.file.Paths;

public class File {
    private byte[] bytes;
    private String fileName;

    public File() {
    }

    public File(byte[] bytes, String fileName) {
        this.setBytes(bytes);
        this.setFileName(fileName);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setByteFromPart(Part part) throws Exception {
        this.setBytes(FileUtils.convertPartToBytes(part));
    }

    public void writeBytesToFile(String filePath) throws IOException {
        if (this.bytes == null) {
            throw new IOException("File content is empty.");
        }
        Path path = Paths.get(filePath, this.fileName);
        Files.write(path, this.bytes);
    }

}
