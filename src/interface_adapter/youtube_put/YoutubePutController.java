package interface_adapter.youtube_put;

import entity.CompletePlaylist;
import use_case.youtube_put.YoutubePutInputBoundary;
import use_case.youtube_put.YoutubePutInputData;

public class YoutubePutController {
    final YoutubePutInputBoundary youtubePutUseCaseInteractor;
    public YoutubePutController(YoutubePutInputBoundary youtubePutUseCaseInteractor) {
        this.youtubePutUseCaseInteractor = youtubePutUseCaseInteractor;
    }

    public void execute(CompletePlaylist playlist, String playlistName) {
        String userID = "317snmsetkbgiao7bxxe3ycmunvi"; // this is the songwarp spotify account
        YoutubePutInputData youtubePutInputData = new YoutubePutInputData(playlist, playlistName, userID);

        youtubePutUseCaseInteractor.execute(youtubePutInputData);
    }
}
