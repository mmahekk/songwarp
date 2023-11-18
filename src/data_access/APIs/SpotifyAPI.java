package data_access.APIs;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class SpotifyAPI {
    public static String clientID = "11d73b5dea134ad89de266eee9b4db5d";
    public static String clientSecret = "5341f28addc940dc83860492caaffdd9";
    public static String apiMainUrl = "https://api.spotify.com/v1/";

    public static String spotifyAPIRequest(String apiRequest, String itemInfo) throws IOException, InterruptedException {
        String accessKey = getSpotifyAccess();
        HttpClient client = HttpClient.newHttpClient();
        String apiCall = null;
        if (Objects.equals(apiRequest, "getPlaylist")) {
            apiCall = "playlists/" + itemInfo;
        } else if (Objects.equals(apiRequest, "searchSong")) {
            apiCall = "search?q=" + itemInfo + "&type=track&limit=1";
        }
        if (apiCall != null) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(apiMainUrl + apiCall))
                        .header("Authorization", "Bearer " + accessKey)
                        .build();

                HttpResponse<String> response = client.send(request,
                        HttpResponse.BodyHandlers.ofString());
                return response.body();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getSpotifyAccess() {
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
