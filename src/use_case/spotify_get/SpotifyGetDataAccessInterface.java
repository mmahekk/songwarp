package use_case.spotify_get;

import data_access.APIs.SpotifyAPIAdapter;
import entity.SpotifyPlaylist;
import org.json.JSONObject;

public interface SpotifyGetDataAccessInterface {

    JSONObject getPlaylistJSON(SpotifyAPIAdapter api, String spotifyPlaylistID);  // makes request to youtube to get a playlist json
    SpotifyPlaylist buildSpotifyPlaylist(JSONObject spotifyPlaylistJSON, String playlistId);
}
