package data_access.YoutubeAPITest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class YouTubePlaylistIdExtractor {
    public static String extractPlaylistId(String playlistUrl) {
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

    public static void main(String[] args) {
        String playlistUrl = "https://youtube.com/playlist?list=PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5&si=jk3rMVrPi_RLrbP6";
        String playlistId = extractPlaylistId(playlistUrl);
        if (playlistId != null) {
            System.out.println("Playlist ID: " + playlistId);
        } else {
            System.out.println("Invalid URL or no playlist ID found.");
        }
    }
}
