package data_access;

import entity.YoutubePlaylist;
import use_case.youtube_get.YoutubeGetDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YoutubeGetDataAccessObject implements YoutubeGetDataAccessInterface {
    @Override
    public String getPlaylistJSON(String youtubePlaylistID) {
        try {
            HttpURLConnection connection = getHttpURLConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();

                // Parse the JSON response
                String jsonResponse = response.toString();
                return jsonResponse;
            } else {
                return "FAILED HTTP request with response code: " + responseCode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection getHttpURLConnection() throws IOException {
        String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
        String playlistId = "PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5";
        String apiUsed = "https://www.googleapis.com/youtube/v3/";
        String apiRequest = "playlistItems?playlistId=";

        // Construct the URL for the API request
        String apiUrl = apiUsed + apiRequest + playlistId + "&key=" + apiKey + "&part=snippet";

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    @Override
    public YoutubePlaylist buildYoutubePlaylist(String youtubePlaylistJSON) {
        // TODO: to be implemented
        return null;
    }
}
