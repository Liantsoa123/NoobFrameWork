package mg.noobframework.modelview;

import java.util.HashMap;

public class Mview {

    private String url;
    private HashMap<String, Object> data;

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Mview(String url, HashMap<String, Object> data) {
        this.setData(data);
        this.setUrl(url);
    }

    public Mview() {
    }

    public void add(String key, Object values) {
        data.put(key, values);
    }

}
