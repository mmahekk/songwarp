package use_case.spotify_get;

import use_case.spotify_get.SpotifyGetOutputData;

public interface SpotifyGetOutputBoundary {

    void prepareSuccessView(SpotifyGetOutputData spotifyGetOutputData);

    void prepareFailView(String error);
}
