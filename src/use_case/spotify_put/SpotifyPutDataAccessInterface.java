package use_case.spotify_put;

import entity.CompletePlaylist;

import java.io.IOException;

public interface SpotifyPutDataAccessInterface {
    String getUserAuthorization();

    String getUserID(String token) throws IOException, InterruptedException;

    String initializeYoutubePlaylist(String userID, String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) throws IOException, InterruptedException;
}
