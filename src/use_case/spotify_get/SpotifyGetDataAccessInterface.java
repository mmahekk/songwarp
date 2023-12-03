package use_case.spotify_get;

import org.json.JSONObject;

public interface SpotifyGetDataAccessInterface {
    JSONObject getPlaylistJSON(String spotifyPlaylistID);  // makes request to youtube to get a playlist json
}
