package use_case.youtube_put;

import data_access.APIs.SpotifyAPIAdapter;
import entity.CompletePlaylist;

import java.io.IOException;

public interface YoutubePutDataAccessInterface {

    String getUserAuthorization(SpotifyAPIAdapter api);

    String getUserID(SpotifyAPIAdapter api, String token) throws IOException, InterruptedException;

    String initializeSpotifyPlaylist(SpotifyAPIAdapter api, String userID, String playlistName, String youtubeUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(SpotifyAPIAdapter api, String spotifyPlaylistID, CompletePlaylist playlist, String token) throws IOException, InterruptedException;
}
