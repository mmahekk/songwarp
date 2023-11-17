package data_access;


import entity.SpotifyPlaylist;
import entity.SpotifySong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.spotify_get.SpotifyGetDataAccessInterface;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyGetDataAccessObject implements SpotifyGetDataAccessInterface {
    public JSONObject getPlaylistJSON(String spotifyPlaylistID){
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().header("Authorization", "Bearer  " + getAccess())
                    .uri(URI.create("https://api.spotify.com/v1/playlists/" + spotifyPlaylistID))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return new JSONObject(response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getAccess() throws IOException, InterruptedException {

        try {
            int counter = 16;
            String key = "";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials&client_id=11d73b5dea134ad89de266eee9b4db5d&client_secret=5341f28addc940dc83860492caaffdd9A"))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());


            while (response.body().charAt(counter) != ',') {
                key += response.body().charAt(counter);
                counter += 1;
            }

            return key;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SpotifyPlaylist buildSpotifyPlaylist(JSONObject spotifyPlaylistJSON, String playlistId) {
        if (spotifyPlaylistJSON.has("error")) {
            return null;
        } else {
            String name = "unknown name";
            JSONArray songlist = spotifyPlaylistJSON.getJSONObject("tracks").getJSONArray("items");


            SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist(name, null, playlistId);

            for (int i = 0; i < songlist.length(); i++) {
                JSONObject entry = songlist.getJSONObject(i);
                JSONObject snippet = entry.getJSONObject("track");

                if (snippet.has("album")) {
                    JSONObject snippet2 = snippet.getJSONObject("album");
                    String title = snippet.getString("name");
                    String author = snippet2.getJSONArray("artists").getJSONObject(0).getString("name");
                    String date = snippet.getString("added_at");
                    String duration = snippet.getString("duration_ms");
                    String id = snippet.getString("id");

                    SpotifySong song = new SpotifySong(title, author, Integer.parseInt(duration), id, date);
                    spotifyPlaylist.addSong(song);
                } else {
                    System.out.println("Deleted.");
                }
            }
            return spotifyPlaylist;
        }
    }
}
