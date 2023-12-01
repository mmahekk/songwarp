package use_case.youtube_put;

import data_access.APIs.SpotifyAPIAdapter;
import entity.CompletePlaylist;
import java.io.IOException;

public class YoutubePutInteractor implements YoutubePutInputBoundary {
    final YoutubePutDataAccessInterface youtubePutDataAccessObject;
    final YoutubePutOutputBoundary youtubePutPresenter;

    public YoutubePutInteractor(YoutubePutDataAccessInterface youtubePutDataAccessObject, YoutubePutOutputBoundary youtubePutPresenter) {
        this.youtubePutDataAccessObject = youtubePutDataAccessObject;
        this.youtubePutPresenter = youtubePutPresenter;
    }

    @Override
    public void execute(YoutubePutInputData youtubePutInputData) {

        String spotifyPlaylistID;
        CompletePlaylist playlist = youtubePutInputData.getPlaylist();
        String name = youtubePutInputData.getPlaylistName();
        String url = youtubePutInputData.getYoutubeUrl();
        if (name != null && !name.isEmpty() && name.matches("^.*[a-zA-Z0-9].*$")) {
            SpotifyAPIAdapter api = new SpotifyAPIAdapter();
            try {
                // create a user authorized token to be used across all calls in this use case (no worry about 1 hour expiration)
                String token = youtubePutDataAccessObject.getUserAuthorization(api);

                String id = youtubePutDataAccessObject.getUserID(api, token);

                spotifyPlaylistID = youtubePutDataAccessObject.initializeSpotifyPlaylist(api, id, name, url, token);
                playlist.setName(youtubePutInputData.getPlaylistName());
                if (spotifyPlaylistID != null) {
                    playlist.setSpotifyID(spotifyPlaylistID);
                    youtubePutDataAccessObject.uploadSongs(api, spotifyPlaylistID, playlist, token);

                    System.out.println("New playlist: https://open.spotify.com/playlist/" + spotifyPlaylistID);

                    YoutubePutOutputData youtubePutOutputData = new YoutubePutOutputData(playlist);
                    youtubePutPresenter.prepareSuccessView(youtubePutOutputData);
                } else {
                    youtubePutPresenter.prepareFailView("Failed to upload the playlist to Spotify with null");
                }
            } catch (IOException | InterruptedException e) {
                youtubePutPresenter.prepareFailView("Failed to upload the playlist to Spotify");
            }
        } else {
            youtubePutPresenter.prepareFailView("Please enter a valid playlist name first.");
        }
    }
}
