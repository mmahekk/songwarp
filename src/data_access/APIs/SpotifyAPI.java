package data_access.APIs;

import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import io.javalin.Javalin;

public class SpotifyAPI extends APIcaller {
    public SpotifyAPI() {
        this.clientID = "11d73b5dea134ad89de266eee9b4db5d";
        this.clientSecret = "5341f28addc940dc83860492caaffdd9";
        this.apiMainUrl = "https://api.spotify.com/v1/";
        this.scope = "playlist-modify-private playlist-modify-public user-read-private playlist-modify";
        this.authCodeUrl = "https://accounts.spotify.com/authorize?";
        this.authTokenExchange = "https://accounts.spotify.com/api/token";
    }

    public String request(InputAPI input) throws IOException {

        HttpClient client = HttpClient.newHttpClient();
        String apiCall = null;
        JSONObject jsonData = new JSONObject();
        switch (input.getApiCall()) {
            case "getPlaylist":
                apiCall = "playlists/" + input.getItemInfo()[0];  // itemInfo[0] is an existing playlist's id
                break;
            case "searchSong":
                apiCall = "search?q=" + input.getItemInfo()[0] + "&type=track&limit=1";  // itemInfo[0] is the search query
                break;
            case "createPlaylist":
                apiCall = "users/" + input.getItemInfo()[0] + "/playlists";  // itemInfo[0] is the user id (by default, it's our shared spotify account)
                jsonData.put("name", input.getItemInfo()[1]);
                jsonData.put("description", "Spotify Playlist converted from "
                        + input.getItemInfo()[2] + " using SongWarp");
                jsonData.put("public", true);
                break;
            case "addSongsToPlaylist":
                apiCall = "playlists/" + input.getItemInfo()[0] + "/tracks";
                jsonData.put("uris", input.getListInfo());
                break;
            case "getUser":
                apiCall = "me";
                break;
            default:
                break;
        }
        if (apiCall != null) {
            try {
                HttpRequest request = null;
                String accessKey;
                if (input.getPremadeToken() != null) {
                    accessKey = input.getPremadeToken();
                } else {
                    accessKey = getSpotifyAccess();
                }
                if (jsonData.isEmpty()) {
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(apiMainUrl + apiCall))
                            .header("Authorization", "Bearer " + accessKey)
                            .build();
                } else {
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(apiMainUrl + apiCall))
                            .header("Authorization", "Bearer " + accessKey)
                            .POST(HttpRequest.BodyPublishers.ofString(jsonData.toString())) // note this line
                            .build();
                }
                if (request != null) {
                    HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    return response.body();
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSpotifyAccess() {
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
