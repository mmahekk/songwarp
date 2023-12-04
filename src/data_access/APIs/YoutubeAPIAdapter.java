package data_access.APIs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class YoutubeAPIAdapter extends APIcaller implements YoutubeAPIAdapterInterface {
    private final String apiKey;

    public YoutubeAPIAdapter() {
        this.apiKey = "AIzaSyDBBye718R0GFZp0Q09jmisniArShBFInI";
        this.apiMainUrl = "https://www.googleapis.com/youtube/v3/";
        this.clientID = "548458776002-b2jqss6dcls5rinb8or5fc7nbbuaijum.apps.googleusercontent.com";
        this.clientSecret = "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";
        this.scope = "https://www.googleapis.com/auth/youtube";
        this.authCodeUrl = "https://accounts.google.com/o/oauth2/v2/auth?";
        this.authTokenExchange = "https://oauth2.googleapis.com/token";
    }

    @Override
    public String getPlaylist(String playlistID, String pageToken) {
        String apiCall = apiMainUrl + "playlistItems?" + "part=snippet&maxResults=50" + "&playlistId=" + playlistID;
        if (pageToken != null) {
            apiCall += "&pageToken=" + pageToken;
        }
        APIRequestInfo info = new APIRequestInfo(apiCall, "GET", apiKey, null);
        return this.request(info);
    }

    @Override
    public String searchSong(String query) {
        String apiCall = apiMainUrl + "search?q=" + query + "&part=snippet" + "&order=relevance" + "&maxResults=1";
        APIRequestInfo info = new APIRequestInfo(apiCall, "GET", apiKey, null);
        return this.request(info);
    }

    @Override
    public String createPlaylist(String name, String relatedUrl, String authKey) {
        String data = "{\"snippet\": {\"title\": \"" + name
                + "\", \"description\": \"YouTube Playlist converted from " + relatedUrl + " using SongWarp\"}"
                + ",\"status\": {\"privacyStatus\": \"unlisted\"}}";
        String apiCall = apiMainUrl + "playlists?" + "part=snippet&part=status";
        APIRequestInfo info = new APIRequestInfo(apiCall, "POST", authKey, data);
        return this.request(info);
    }

    @Override
    public void addSongToPlaylist(String playlistID, String songID, String authKey) {
        String data = "{\"snippet\":{\"playlistId\":\"" + playlistID
                + "\",\"resourceId\":{\"videoId\": \"" + songID
                + "\",\"kind\": \"youtube#video\"}}}";
        String apiCall = apiMainUrl + "playlistItems?" + "part=snippet";
        APIRequestInfo info = new APIRequestInfo(apiCall, "POST", authKey, data);
        this.request(info);
    }

    public String request(APIRequestInfo info) {
        String apiCall = info.getUri();
        String apiCallMethod = info.getCallMethod();
        String keyToUse = info.getKey();
        String data = info.getData();
        if (apiCall != null) {
            // Construct the URL for the API request
            String apiUrl = apiCall;
            if (!Objects.equals(apiCallMethod, "POST")) {
                apiUrl += "&key=" + keyToUse;
            }
            URL url = null;
            try {
                url = new URL(apiUrl);
            } catch (MalformedURLException e) {return null;}

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(apiCallMethod);
                if (data != null) {
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
            } catch (IOException e) {}
        }
        return null;
    }
}
