package use_case.youtube_put;

import entity.CompletePlaylist;

import java.io.IOException;

public interface YoutubePutDataAccessInterface {

    String getUserAuthorization();

    String getUserID(String token) throws IOException, InterruptedException;

    String initializeSpotifyPlaylist(String userID, String playlistName, String youtubeUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(String spotifyPlaylistID, CompletePlaylist playlist, String token) throws IOException, InterruptedException;
}
