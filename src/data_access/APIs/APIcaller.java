package data_access.APIs;

import io.javalin.Javalin;
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

public abstract class APIcaller {
    public String apiMainUrl;
    public String clientID;
    public String clientSecret;
    public String redirectURI = "http://localhost:3000/callback";
    public String otherRedirectURI = "http://localhost:3000/login";
    public String scope;
    public String authCodeUrl;
    public String authTokenExchange;

    public abstract String request(InputAPI input) throws IOException;

    public String getUserAuthAccessToken() {
        try {
            // user auth
            ConcurrentHashMap<String, String> stateMap = new ConcurrentHashMap<>();
            Javalin app = Javalin.create().start(3000);
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<String> key = new AtomicReference<>("");

            app.get("/login", ctx -> {
                String state = generateRandomString(16);
                stateMap.put(state, "unused"); // Storing state in memory, replace this with a more secure storage in a real application

                String authorizationUrl = authCodeUrl + "response_type=code" + "&" +
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
                                .uri(URI.create(authTokenExchange))
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
