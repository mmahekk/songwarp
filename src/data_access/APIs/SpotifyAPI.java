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

public class SpotifyAPI {
    public static String clientID = "11d73b5dea134ad89de266eee9b4db5d";
    public static String clientSecret = "5341f28addc940dc83860492caaffdd9";
    public static String apiMainUrl = "https://api.spotify.com/v1/";
    public static String redirectURI = "http://localhost:3000/callback";
    public static String otherRedirectURI = "http://localhost:3000/login";

    public static String spotifyAPIRequest(InputAPI input) throws IOException, InterruptedException {

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

    public static String getUserAuthAccessToken() {
        String scope = "playlist-modify-private playlist-modify-public user-read-private playlist-modify";
        try {
            // user auth
            ConcurrentHashMap<String, String> stateMap = new ConcurrentHashMap<>();
            Javalin app = Javalin.create().start(3000);
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<String> key = new AtomicReference<>("");

            app.get("/login", ctx -> {
                String state = generateRandomString(16);
                stateMap.put(state, "unused"); // Storing state in memory, replace this with a more secure storage in a real application

                String authorizationUrl = "https://accounts.spotify.com/authorize?" +
                        "response_type=code&" +
                        "client_id=" + clientID + "&" +
                        "scope=" + scope + "&" +
                        "redirect_uri=" + redirectURI + "&" +
                        "state=" + state;
                ctx.redirect(authorizationUrl);
            });
            // Handle callback from Spotify after user authorization
            app.get("/callback", ctx -> {
                String state = ctx.queryParam("state");
                String code = ctx.queryParam("code");
                if (state != null && stateMap.containsKey(state)) {
                    stateMap.remove(state); // Remove the state after use for security reasons
                    try {
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .uri(URI.create("https://accounts.spotify.com/api/token"))
                                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code="
                                        + code + "&redirect_uri=" + redirectURI + "&client_id=" + clientID + "&client_secret=" + clientSecret))
                                .build();

                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        key.set(response.body());
                        ctx.html("Authorization successful! You can now close this and return to the program.");
                    } catch (Exception e) {
                        System.err.println("Error getting access token: " + e.getMessage());
                        ctx.html("Authorization failed because of " + e.getMessage());
                    } finally {
                        latch.countDown(); // Countdown the latch after processing the callback
                    }
                } else {
                    ctx.html("Invalid state. Authorization failed.");
                }
            });

            try {
                openBrowser(otherRedirectURI);
                latch.await();  // wait for user to accept (not working now?)
                JSONObject jsonResponse = new JSONObject(key.toString());
                String token = jsonResponse.getString("access_token");
                app.stop();
                return token;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    private static String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    private static void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop not supported or unable to browse.");
        }
    }
}
