package data_access;

import data_access.APIs.SpotifyAPIAdapterInterface;
import org.json.JSONObject;
import use_case.spotify_get.SpotifyGetDataAccessInterface;

public class SpotifyGetDataAccessObject implements SpotifyGetDataAccessInterface {
    final SpotifyAPIAdapterInterface api;

    public SpotifyGetDataAccessObject(SpotifyAPIAdapterInterface api) {
        this.api = api;
    }

    public JSONObject getPlaylistJSON(String spotifyPlaylistID){
        String response = api.getPlaylist(spotifyPlaylistID);
        return new JSONObject(response);
    }
}
