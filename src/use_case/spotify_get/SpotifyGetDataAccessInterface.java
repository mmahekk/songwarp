package use_case.spotify_get;

import data_access.APIs.SpotifyAPI;
import data_access.APIs.YoutubeAPI;
import entity.SpotifyPlaylist;
import org.json.JSONObject;

import java.io.IOException;

public interface SpotifyGetDataAccessInterface {

    JSONObject getPlaylistJSON(SpotifyAPI api, String spotifyPlaylistID);  // makes request to youtube to get a playlist json
    SpotifyPlaylist buildSpotifyPlaylist(JSONObject spotifyPlaylistJSON, String playlistId);
}
