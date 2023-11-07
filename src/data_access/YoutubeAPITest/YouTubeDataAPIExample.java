package data_access.YoutubeAPITest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class YouTubeDataAPIExample {
    public static void main(String[] args) {
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
                // System.out.println(jsonResponse);
                // Process the JSON data as needed
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray songList = jsonObject.getJSONArray("items");
                for (int i = 0; i < songList.length(); i++) {
                    JSONObject song = songList.getJSONObject(i);
                    JSONObject snippet = song.getJSONObject("snippet");

                    if (snippet.has("videoOwnerChannelTitle")) {
                        String title = snippet.getString("title");
                        String channel = snippet.getString("videoOwnerChannelTitle");
                        String date = snippet.getString("publishedAt");
                        JSONObject extraInfo = snippet.getJSONObject("resourceId");
                        String id = extraInfo.getString("videoId");

                        System.out.println("title: " + title + ", channel: " + channel + ", date: " + date + ", videoId: " + id);
                    } else {
                        System.out.println("There was a deleted video here");
                    }
                }

                //TODO: write the json output into a json file
                // or return the json output
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HttpURLConnection getHttpURLConnection() throws IOException {
        String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
        String playlistId = "PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5";
        String apiUsed = "https://www.googleapis.com/youtube/v3/";
        String apiRequest = "playlistItems?";
        String configurations = "part=snippet&maxResults=50";

        // Construct the URL for the API request
        String apiUrl = apiUsed + apiRequest + configurations + "&playlistId=" + playlistId + "&key=" + apiKey;

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }
}