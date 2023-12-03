package use_case.spotify_get;

import data_access.APIs.SpotifyAPIAdapter;
import org.json.JSONObject;

public interface SpotifyGetDataAccessInterface {

    JSONObject getPlaylistJSON(SpotifyAPIAdapter api, String spotifyPlaylistID);  // makes request to youtube to get a playlist json
}
