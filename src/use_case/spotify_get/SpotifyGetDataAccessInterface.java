package use_case.spotify_get;

import entity.SpotifyPlaylist;

import java.io.IOException;

public interface SpotifyGetDataAccessInterface {

    String getPlaylistJSON(String spotifyPlaylistID);  // makes request to youtube to get a playlist json
    SpotifyPlaylist buildSpotifyPlaylist(String spotifyPlaylistJSON, String playlistId);
}
