package interface_adapter.spotify_put;

import entity.CompletePlaylist;
import use_case.spotify_put.SpotifyPutInputBoundary;
import use_case.spotify_put.SpotifyPutInputData;
import use_case.youtube_put.YoutubePutInputData;

public class SpotifyPutController {
    final SpotifyPutInputBoundary spotifyPutUseCaseInteractor;
    public SpotifyPutController(SpotifyPutInputBoundary spotifyPutUseCaseInteractor) {
        this.spotifyPutUseCaseInteractor = spotifyPutUseCaseInteractor;
    }

    public void execute(CompletePlaylist playlist, String playlistName) {
        String userID = "317snmsetkbgiao7bxxe3ycmunvi"; // this is the songwarp spotify account
        SpotifyPutInputData spotifyPutInputData = new SpotifyPutInputData(playlist, playlistName, userID);

        spotifyPutUseCaseInteractor.execute(spotifyPutInputData);
    }
}
