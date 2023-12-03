package interface_adapter.spotify_match;

import entity.Playlist;
import entity.SpotifyPlaylist;

import use_case.spotify_match.SpotifyMatchInputBoundary;
import use_case.spotify_match.SpotifyMatchInputData;

public class SpotifyMatchController {
    final SpotifyMatchInputBoundary spotifyMatchUseCaseInteractor;
    public SpotifyMatchController(SpotifyMatchInputBoundary spotifyMatchUseCaseInteractor) {
        this.spotifyMatchUseCaseInteractor = spotifyMatchUseCaseInteractor;
    }

    public void execute(SpotifyPlaylist playlist, Boolean gotoNextView) {
        SpotifyMatchInputData spotifyMatchInputData = new SpotifyMatchInputData(playlist, null, gotoNextView, -1);


        // call use case interactor
        spotifyMatchUseCaseInteractor.execute(spotifyMatchInputData);
    }

    public void execute(SpotifyPlaylist playlist, Playlist incompletePlaylist, Boolean gotoNextView) {
        SpotifyMatchInputData spotifyMatchInputData = new SpotifyMatchInputData(playlist, incompletePlaylist, gotoNextView, -1);

        // call use case interactor
       spotifyMatchUseCaseInteractor.execute(spotifyMatchInputData);
    }

    public void execute(SpotifyPlaylist playlist, Playlist incompletePlaylist, Boolean gotoNextView, int songLimit) {
        SpotifyMatchInputData spotifyMatchInputData = new SpotifyMatchInputData(playlist, incompletePlaylist, gotoNextView, songLimit);
        // call use case interactor
        spotifyMatchUseCaseInteractor.execute(spotifyMatchInputData);
    }
}
