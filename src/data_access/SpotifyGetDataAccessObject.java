package data_access;

import data_access.APIs.SpotifyAPIAdapter;
import org.json.JSONObject;
import use_case.spotify_get.SpotifyGetDataAccessInterface;

public class SpotifyGetDataAccessObject implements SpotifyGetDataAccessInterface {
    public JSONObject getPlaylistJSON(SpotifyAPIAdapter api, String spotifyPlaylistID){
        String response = api.getPlaylist(spotifyPlaylistID);
        return new JSONObject(response);
    }

}
