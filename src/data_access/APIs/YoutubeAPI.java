package data_access.APIs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class YoutubeAPI extends APIcaller {
    private final String apiKey;

    public YoutubeAPI() {
        this.apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
        this.apiMainUrl = "https://www.googleapis.com/youtube/v3/";
        this.clientID = "792243017341-0f3m3ejt4u27smegq429ubsmanf10reh.apps.googleusercontent.com";
        this.clientSecret = "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";
        this.scope = "https://www.googleapis.com/auth/youtube";
        this.authCodeUrl = "https://accounts.google.com/o/oauth2/v2/auth?";
        this.authTokenExchange = "https://oauth2.googleapis.com/token";
    }

    public String request(InputAPI input) throws IOException {
        String apiCall = null;
        String apiCallMethod = null;
        String data = "";
        String key = apiKey;
        switch (input.getApiCall()) {
            case "getPlaylist":
                apiCall =  "playlistItems?" + "part=snippet&maxResults=50" + "&playlistId=" + input.getItemInfo()[0];
                if (input.getItemInfo().length == 2) {
                    apiCall += "&pageToken=" + input.getItemInfo()[1];
                }
                apiCallMethod = "GET";
                break;
            case "searchSong":
                apiCall = "search?q=" + input.getItemInfo()[0] + "&part=snippet" + "&order=relevance" + "&maxResults=1";
                apiCallMethod = "GET";
                break;
            case "createPlaylist":
                data = "{\"snippet\": {\"title\": \"" + input.getItemInfo()[0]
                        + "\", \"description\": \"YouTube Playlist converted from " + input.getItemInfo()[1] + " using SongWarp\"}"
                        + ",\"status\": {\"privacyStatus\": \"unlisted\"}}";
                apiCall = "playlists?" + "part=snippet&part=status";
                apiCallMethod = "POST";
                break;
            case "addSongToPlaylist":
                data = "{\"snippet\":{\"playlistId\":\"" + input.getItemInfo()[0]
                        + "\",\"resourceId\":{\"videoId\": \"" + input.getItemInfo()[1]
                        + "\",\"kind\": \"youtube#video\"}}}";
                apiCall = "playlistItems?" + "part=snippet";
                apiCallMethod = "POST";
                break;
            default:
                break;
        }
        if (Objects.equals(apiCallMethod, "POST")) {
            key = input.getPremadeToken();
        }
        return getHttpURLConnection(apiCall, apiCallMethod, key, data);
    }

    private String getHttpURLConnection(String apiCall, String apiCallMethod, String keyToUse, String data) throws IOException {
        if (apiCall != null) {
            // Construct the URL for the API request
            String apiUrl = apiMainUrl + apiCall;
            if (!Objects.equals(apiCallMethod, "POST")) {
                apiUrl += "&key=" + keyToUse;
            }
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(apiCallMethod);
            if (!data.isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + keyToUse);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
                connection.setDoOutput(true);

                // Write JSON data to the request body using OutputStreamWriter
                try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {
                    writer.write(data);
                }
            }
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                return response.toString();
            } else {
                System.out.println("FAILED HTTP request with response code: " + responseCode);
                return null;
            }
        } else {
            return null;
        }
    }
}
