package use_case.spotify_match;

import entity.CompletePlaylist;
import entity.SpotifyPlaylist;
import use_case.spotify_match.SpotifyMatchOutputData;

public interface SpotifyMatchOutputBoundary {

    void prepareSuccessView(SpotifyMatchOutputData spotifyMatchOutputData, Boolean gotoNextView);

    void prepareFailView(String error);

    void failSaveExit(SpotifyPlaylist playlist, CompletePlaylist matchedPlaylist, String error);

}
