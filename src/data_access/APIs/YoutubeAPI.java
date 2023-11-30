package data_access.APIs;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class YoutubeAPI {
    public static String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
    public static String apiMainUrl = "https://www.googleapis.com/youtube/v3/";
    public static String clientID = "792243017341-0f3m3ejt4u27smegq429ubsmanf10reh.apps.googleusercontent.com";
    public static String clientSecret = "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";
    public static String redirectURI = "http://localhost:3000/callback";
    public static String otherRedirectURI = "http://localhost:3000/login";

    /**
     *This version uses a pageToken too
     */
    public static String youtubeAPIRequest(InputAPI input) throws IOException {
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
                //TODO: add this case
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

    private static String getHttpURLConnection(String apiCall, String apiCallMethod, String keyToUse, String data) throws IOException {
        if (apiCall != null) {
            // Construct the URL for the API request
            String apiUrl = apiMainUrl + apiCall;
            if (data.isEmpty()) {
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

    public static String getUserAuthAccessToken() {
        String scope = "https://www.googleapis.com/auth/youtube";
        try {
            // user auth
            ConcurrentHashMap<String, String> stateMap = new ConcurrentHashMap<>();
            Javalin app = Javalin.create().start(3000);
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<String> key = new AtomicReference<>("");

            app.get("/login", ctx -> {
                String state = generateRandomString(16);
                stateMap.put(state, "unused"); // Storing state in memory, replace this with a more secure storage in a real application

                String authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
                        + "response_type=code" + "&" +
                        "client_id=" + clientID + "&" +
                        "scope=" + scope + "&" +
                        "redirect_uri=" + redirectURI + "&" +
                        "state=" + state + "&" +
                        "access_type=offline";
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
                                .uri(URI.create("https://oauth2.googleapis.com/token"))
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
