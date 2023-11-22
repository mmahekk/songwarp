package data_access.APIs;

import org.json.JSONArray;

public class InputSpotifyAPI {
    private String apiCall;
    private String[] itemInfo;
    private JSONArray listInfo;

    private String premadeToken;

    public String getApiCall() {
        return apiCall;
    }

    public JSONArray getListInfo() {
        return listInfo;
    }

    public String[] getItemInfo() {
        return itemInfo;
    }

    public void setApiCall(String apiCall) {
        this.apiCall = apiCall;
    }

    public void setItemInfo(String[] itemInfo) {
        this.itemInfo = itemInfo;
    }

    public void setListInfo(JSONArray listInfo) {
        this.listInfo = listInfo;
    }

    public String getPremadeToken() {
        return premadeToken;
    }

    public void setPremadeToken(String premadeToken) {
        this.premadeToken = premadeToken;
    }
}
