package interface_adapter.spotify_get;


import use_case.spotify_get.SpotifyGetInputBoundary;
import use_case.spotify_get.SpotifyGetInputData;
import use_case.youtube_get.YoutubeGetInputBoundary;

public class SpotifyGetController {

    final SpotifyGetInputBoundary spotifyGetUseCaseInteractor;
    public SpotifyGetController(SpotifyGetInputBoundary spotifyGetUseCaseInteractor) {
        this.spotifyGetUseCaseInteractor = spotifyGetUseCaseInteractor;
    }

    public void execute(String url) {
        SpotifyGetInputData spotifyGetInputData = new SpotifyGetInputData(url);

        //invoke the use case interactor
        spotifyGetUseCaseInteractor.execute(spotifyGetInputData);
    }
}
