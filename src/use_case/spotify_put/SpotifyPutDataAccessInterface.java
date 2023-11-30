package use_case.spotify_put;

import data_access.APIs.YoutubeAPI;
import entity.CompletePlaylist;

import java.io.IOException;

public interface SpotifyPutDataAccessInterface {
    String getUserAuthorization(YoutubeAPI api);

    String initializeYoutubePlaylist(YoutubeAPI api, String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(YoutubeAPI api, String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) throws IOException, InterruptedException;

    int getExistingPlaylistOffset(YoutubeAPI api, String youtubePlaylistID) throws IOException;
}
