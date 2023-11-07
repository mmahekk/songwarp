package use_case.youtube_get;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class YoutubeGetInputData {
    final private String url;
    final private String id;
    public YoutubeGetInputData(String url) {
        this.url = url;
        this.id = this.extractId(url);
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String extractId(String playlistUrl) {
        try {
            URL url = new URL(playlistUrl);
            String query = url.getQuery();
            if (query != null) {
                Map<String, String> queryMap = new HashMap<>();
                String[] params = query.split("&");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    if (keyValue.length == 2) {
                        queryMap.put(keyValue[0], keyValue[1]);
                    }
                }
                if (queryMap.containsKey("list")) {
                    return queryMap.get("list");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
