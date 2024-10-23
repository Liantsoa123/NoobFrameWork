package mg.noobframework.file;

import jakarta.servlet.http.Part;

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

}
