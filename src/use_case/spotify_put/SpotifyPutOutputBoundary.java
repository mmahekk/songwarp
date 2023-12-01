package use_case.spotify_put;

import use_case.youtube_put.YoutubePutOutputData;

public interface SpotifyPutOutputBoundary {
    void prepareSuccessView(SpotifyPutOutputData spotifyPutOutputData);

    void prepareFailView(String error);
}
