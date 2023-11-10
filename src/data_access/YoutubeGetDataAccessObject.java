package data_access;

import entity.YoutubePlaylist;
import entity.YoutubeSong;
import org.json.JSONArray;
import org.json.JSONObject;
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
            HttpURLConnection connection = getHttpURLConnection(youtubePlaylistID);

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

    private static HttpURLConnection getHttpURLConnection(String playlistId) throws IOException {
        String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
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

    @Override
    public YoutubePlaylist buildYoutubePlaylist(String youtubePlaylistJSON, String playlistId) {
        // convert JSON string to JSON object
        JSONObject jsonObject = new JSONObject(youtubePlaylistJSON);
        String name = "unknown name";
        JSONArray songList = jsonObject.getJSONArray("items");

        // create empty youtubePlaylist object
        YoutubePlaylist youtubePlaylist = new YoutubePlaylist(name, null, playlistId);

        for (int i = 0; i < songList.length(); i++) {
            JSONObject entry = songList.getJSONObject(i);
            JSONObject snippet = entry.getJSONObject("snippet");

            if (snippet.has("videoOwnerChannelTitle")) {
                String title = snippet.getString("title");
                String channel = snippet.getString("videoOwnerChannelTitle");
                String date = snippet.getString("publishedAt");
                JSONObject extraInfo = snippet.getJSONObject("resourceId");
                String id = extraInfo.getString("videoId");

                YoutubeSong song = new YoutubeSong(title, channel, id, date);
                youtubePlaylist.addSong(song);
            } else {
                System.out.println("There was a deleted video here");
            }
        }
        return youtubePlaylist;
    }
}