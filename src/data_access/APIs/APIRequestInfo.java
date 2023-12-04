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

    public String getCallMethod() {
        return callMethod;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }
}
