package use_case.youtube_put;

import data_access.APIs.SpotifyAPI;
import entity.CompletePlaylist;

import java.io.IOException;

public interface YoutubePutDataAccessInterface {

    String getUserAuthorization(SpotifyAPI api);

    String getUserID(SpotifyAPI api, String token) throws IOException, InterruptedException;

    String initializeSpotifyPlaylist(SpotifyAPI api, String userID, String playlistName, String youtubeUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(SpotifyAPI api, String spotifyPlaylistID, CompletePlaylist playlist, String token) throws IOException, InterruptedException;
}
