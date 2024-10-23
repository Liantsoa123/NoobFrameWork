package mg.noobframework.file;

public class File {
    private byte[] bytes; 
    private String filePath ;
    public File() {
    }
    public File(byte[] bytes, String filePath) {
        this.setBytes(bytes);
        this.setFilePath(filePath);
    }
    public byte[] getBytes() {
        return bytes;
    }
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
