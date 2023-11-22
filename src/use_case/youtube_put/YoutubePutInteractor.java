package use_case.youtube_put;

import entity.CompletePlaylist;
import use_case.youtube_match.YoutubeMatchOutputData;

import java.io.IOException;

import static data_access.APIs.SpotifyAPI.getUserAuthAccessToken;

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
        try {
            // create a user authorized token to be used across all calls in this use case (no worry about 1 hour expiration)
            String token = youtubePutDataAccessObject.getUserAuthorization();

            String id = youtubePutDataAccessObject.getUserID(token);

            spotifyPlaylistID = youtubePutDataAccessObject.initializeSpotifyPlaylist(id,
                    youtubePutInputData.getPlaylistName(), youtubePutInputData.getYoutubeUrl(), token);
            playlist.setName(youtubePutInputData.getPlaylistName());
            playlist.setSpotifyID(spotifyPlaylistID);
            youtubePutDataAccessObject.uploadSongs(spotifyPlaylistID, playlist, token);

            System.out.println("New playlist: https://open.spotify.com/playlist/" + spotifyPlaylistID);

            YoutubePutOutputData youtubePutOutputData = new YoutubePutOutputData(playlist);
            youtubePutPresenter.prepareSuccessView(youtubePutOutputData);
        } catch (IOException | InterruptedException e) {
            youtubePutPresenter.prepareFailView("Failed to upload the playlist to Spotify");
        }
    }
}
