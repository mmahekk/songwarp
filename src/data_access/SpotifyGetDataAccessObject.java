package data_access;

import data_access.APIs.InputAPI;
import data_access.APIs.SpotifyAPI;
import entity.SpotifyPlaylist;
import entity.SpotifySong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.spotify_get.SpotifyGetDataAccessInterface;

import java.io.IOException;

public class SpotifyGetDataAccessObject implements SpotifyGetDataAccessInterface {
    public JSONObject getPlaylistJSON(SpotifyAPI api, String spotifyPlaylistID){
        try {
            InputAPI info = new InputAPI();
            info.setApiCall("getPlaylist");
            info.setItemInfo(new String[]{spotifyPlaylistID});
            String response = api.request(info);
            assert response != null;
            return new JSONObject(response);

        } catch (IOException e) {
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
                    String date = snippet2.getString("release_date");
                    Integer duration = snippet.getInt("duration_ms");
                    String id = snippet.getString("id");

                    SpotifySong song = new SpotifySong(title, author, duration, id, date);
                    spotifyPlaylist.addSong(song);
                } else {
                    System.out.println("Deleted.");
                }
            }
            return spotifyPlaylist;
        }
    }
}
