package use_case.spotify_put;

import entity.CompletePlaylist;

import java.io.IOException;

public class SpotifyPutInteractor implements SpotifyPutInputBoundary {
    final SpotifyPutDataAccessInterface spotifyPutDataAccessObject;
    final SpotifyPutOutputBoundary spotifyPutPresenter;

    public SpotifyPutInteractor(SpotifyPutDataAccessInterface spotifyPutDataAccessObject, SpotifyPutOutputBoundary spotifyPutPresenter) {
        this.spotifyPutDataAccessObject = spotifyPutDataAccessObject;
        this.spotifyPutPresenter = spotifyPutPresenter;
    }

    @Override
    public void execute(SpotifyPutInputData spotifyPutInputData) {
        String youtubePlaylistID;
        CompletePlaylist playlist = spotifyPutInputData.getPlaylist();
        String name = spotifyPutInputData.getPlaylistName();
        String url = spotifyPutInputData.getSpotifyUrl();
        if (name != null && !name.isEmpty() && name.matches("^.*[a-zA-Z0-9].*$")) {
            try {
                // create a user authorized token to be used across all calls in this use case (no worry about 1 hour expiration)
                String token = spotifyPutDataAccessObject.getUserAuthorization();

                int offset = 0;
                if (!playlist.getIDs()[0].equals("unknown")) {
                    String id = spotifyPutDataAccessObject.getUserID(token);
                    youtubePlaylistID = spotifyPutDataAccessObject.initializeYoutubePlaylist(id, name, url, token);
                    playlist.setName(spotifyPutInputData.getPlaylistName());
                    playlist.setSpotifyID(youtubePlaylistID);
                } else {
                    // get playlist request to see how many songs were uploaded so far to this ol' playlist
                    // this could happen if you run out of tokens while uploading it to youtube
                    youtubePlaylistID = url;
                    offset = 1;
                }

                if (youtubePlaylistID != null) {
                    spotifyPutDataAccessObject.uploadSongs(youtubePlaylistID, playlist, token, offset);

                    System.out.println("New playlist: https://www.youtube.com/playlist?list=" + youtubePlaylistID);

                    SpotifyPutOutputData spotifyPutOutputData = new SpotifyPutOutputData(playlist);
                    spotifyPutPresenter.prepareSuccessView(spotifyPutOutputData);
                } else {
                    spotifyPutPresenter.prepareFailView("Failed to upload the playlist to Youtube with null");
                }
            } catch (IOException | InterruptedException e) {
                spotifyPutPresenter.prepareFailView("Failed to upload the playlist to Youtube");
            }
        } else {
            spotifyPutPresenter.prepareFailView("Please enter a valid playlist name first.");
        }
    }
}
