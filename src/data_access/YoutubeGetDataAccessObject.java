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
import java.util.ArrayList;

public class YoutubeGetDataAccessObject implements YoutubeGetDataAccessInterface {
    @Override
    public JSONObject getPlaylistJSON(String youtubePlaylistID) {
        try {
            HttpURLConnection connection = getHttpURLConnection(youtubePlaylistID, null);

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
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            } else {
                String response = "FAILED HTTP request with response code: " + responseCode;
                return new JSONObject(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection getHttpURLConnection(String playlistId, String pageToken) throws IOException {
        String apiKey = "AIzaSyDSuUFqX_f7v1LI8OTYjvkCjTbzzOfj4b4";
        String apiUsed = "https://www.googleapis.com/youtube/v3/";
        String apiRequest = "playlistItems?";
        String configurations = "part=snippet&maxResults=50";

        // Construct the URL for the API request
        String apiUrl = apiUsed + apiRequest + configurations + "&playlistId=" + playlistId + "&key=" + apiKey;
        if (pageToken != null) {
            apiUrl += "&pageToken=" + pageToken;
        }

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * uses next page tokens to get the rest of the playlist, since max return is 50 songs
     *
     * @return
     */
    @Override
    public JSONArray getAllPlaylist(JSONObject jsonObject, String playlistID) {
        String nextPageToken = jsonObject.getString("nextPageToken");
        JSONArray firstItems = jsonObject.getJSONArray("items");
        while (nextPageToken != null) {
            System.out.println(nextPageToken);
            try {
                HttpURLConnection connection = getHttpURLConnection(playlistID, nextPageToken);

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
                    JSONObject nextPage = new JSONObject(response.toString());
                    // get next page token ready for next time
                    if (nextPage.has("nextPageToken")) {
                        nextPageToken = nextPage.getString("nextPageToken");
                    } else {
                        nextPageToken = null;
                        System.out.println("no next page");
                    }

                    if (nextPage.has("items")) {
                        // get items and append them to our JSON array
                        JSONArray newItems = nextPage.getJSONArray("items");
                        for (int i = 0; i < newItems.length(); i++) {
                            firstItems.put(newItems.getJSONObject(i));
                        }
                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return firstItems;
    }

    @Override
    public YoutubePlaylist buildYoutubePlaylist(JSONArray songList, String playlistId) {
        // convert JSON string to JSON object
        String name = "unknown name";

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