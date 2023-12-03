package use_case.spotify_put;

import data_access.APIs.YoutubeAPIAdapter;
import entity.CompletePlaylist;

import java.io.IOException;

public interface SpotifyPutDataAccessInterface {
    String getUserAuthorization(YoutubeAPIAdapter api);

    String initializeYoutubePlaylist(YoutubeAPIAdapter api, String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException;

    void uploadSongs(YoutubeAPIAdapter api, String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) throws IOException, InterruptedException;

    int getExistingPlaylistOffset(YoutubeAPIAdapter api, String youtubePlaylistID) throws IOException;
}
