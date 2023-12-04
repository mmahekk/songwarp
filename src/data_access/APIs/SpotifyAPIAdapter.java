package data_access.APIs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class SpotifyAPIAdapter extends APIcaller implements SpotifyAPIAdapterInterface {
    public SpotifyAPIAdapter() {
        this.clientID = "11d73b5dea134ad89de266eee9b4db5d";
        this.clientSecret = "5341f28addc940dc83860492caaffdd9";
        this.apiMainUrl = "https://api.spotify.com/v1/";
        this.scope = "playlist-modify-private playlist-modify-public user-read-private playlist-modify";
        this.authCodeUrl = "https://accounts.spotify.com/authorize?";
        this.authTokenExchange = "https://accounts.spotify.com/api/token";
    }

    @Override
    public String getPlaylist(String playlistID) {
        String apiCall = apiMainUrl + "playlists/" + playlistID;
        APIRequestInfo info = new APIRequestInfo(apiCall, "GET", null, null);
        return this.request(info);
    }

    @Override
    public String searchSong(String query) {
        String apiCall = apiMainUrl + "search?q=" + query + "&type=track&limit=1";
        APIRequestInfo info = new APIRequestInfo(apiCall, "GET", null, null);
        return this.request(info);
    }

    @Override
    public String createPlaylist(String userID, String name, String relatedUrl, String key) {
        String apiCall = apiMainUrl + "users/" + userID + "/playlists";
        JSONObject data = new JSONObject();
        data.put("name", name);
        data.put("description", "Spotify Playlist converted from " + relatedUrl + " using SongWarp");
        data.put("public", true);
        APIRequestInfo info = new APIRequestInfo(apiCall, "POST", key, data.toString());
        return this.request(info);
    }

    @Override
    public String addSongsToPlaylist(String playlistID, JSONArray songs, String key) {
        String apiCall = apiMainUrl + "playlists/" + playlistID + "/tracks";
        JSONObject data = new JSONObject();
        data.put("uris", songs);
        APIRequestInfo info = new APIRequestInfo(apiCall, "POST", key, data.toString());
        return this.request(info);
    }

    @Override
    public String getUser(String key) {
        String apiCall = apiMainUrl + "me";
        APIRequestInfo info = new APIRequestInfo(apiCall, "GET", key, null);
        return this.request(info);
    }

    @Override
    public String request(APIRequestInfo info) {
        HttpClient client = HttpClient.newHttpClient();
        String apiCall = info.getUri();
        String apiCallMethod = info.getCallMethod();
        String key = info.getKey();
        String data = info.getData();
        try {
            HttpRequest request = null;
            String accessKey;
            if (key == null) {
                accessKey = getSpotifyAccess();
            } else {
                accessKey = key;
            }
            if (Objects.equals(apiCallMethod, "GET")) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(apiCall))
                        .header("Authorization", "Bearer " + accessKey)
                        .build();
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(apiCall))
                        .header("Authorization", "Bearer " + accessKey)
                        .POST(HttpRequest.BodyPublishers.ofString(data)) // note this line
                        .build();
            }
            if (request != null) {
                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                return response.body();
            }
        } catch (IOException | InterruptedException e) {}
        return null;
    }

    private String getSpotifyAccess() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=" + clientID + "&client_secret=" + clientSecret))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getString("access_token");
        } catch (IOException | InterruptedException e) {return null;}
    }
}
