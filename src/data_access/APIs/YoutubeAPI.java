package data_access.APIs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class YoutubeAPI {
    public static String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
    public static String apiMainUrl = "https://www.googleapis.com/youtube/v3/";

    /**
     *This version uses a pageToken too
     */
    public static String youtubeAPIRequest(String apiRequest, String itemID,
                                                      String pageToken) throws IOException {
        String apiCall = null;
        String apiCallMethod = null;
        if (Objects.equals(apiRequest, "getPlaylist")) {
            apiCall =  "playlistItems?" + "part=snippet&maxResults=50" + "&playlistId=" + itemID + "&pageToken=" + pageToken;
            apiCallMethod = "GET";
        } else {

        }
        return getHttpURLConnection(apiCall, apiCallMethod);
    }

    public static String youtubeAPIRequest(String apiRequest, String itemInfo) throws IOException {
        String apiCall = null;
        String apiCallMethod = null;
        if (Objects.equals(apiRequest, "getPlaylist")) {
            apiCall = "playlistItems?" + "part=snippet&maxResults=50" + "&playlistId=" + itemInfo;
            apiCallMethod = "GET";
        } else {

        }
        return getHttpURLConnection(apiCall, apiCallMethod);
    }


    private static String getHttpURLConnection(String apiCall, String apiCallMethod) throws IOException {
        if (apiCall != null) {
            // Construct the URL for the API request
            String apiUrl = apiMainUrl + apiCall + "&key=" + apiKey;
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(apiCallMethod);

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
