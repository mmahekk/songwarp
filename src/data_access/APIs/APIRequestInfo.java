package data_access.APIs;

public class APIRequestInfo {
    private String uri;
    private String callMethod;
    private String key;
    private String data;

    public APIRequestInfo(String uri, String callMethod, String key, String data) {
        this.uri = uri;
        this.callMethod = callMethod;
        this.key = key;
        this.data = data;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCallMethod() {
        return callMethod;
    }

    public void setCallMethod(String callMethod) {
        this.callMethod = callMethod;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
