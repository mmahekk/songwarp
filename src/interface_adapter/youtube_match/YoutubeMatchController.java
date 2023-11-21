package interface_adapter.youtube_match;

import entity.YoutubePlaylist;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchInputBoundary;
import use_case.youtube_match.YoutubeMatchInputData;

public class YoutubeMatchController {
    final YoutubeMatchInputBoundary youtubeMatchUseCaseInteractor;
    public YoutubeMatchController(YoutubeMatchInputBoundary youtubeMatchUseCaseInteractor) {
        this.youtubeMatchUseCaseInteractor = youtubeMatchUseCaseInteractor;
    }

    public void execute(YoutubePlaylist playlist, Boolean gotoNextView) {
        YoutubeMatchInputData youtubeMatchInputData = new YoutubeMatchInputData(playlist);

        //invoke the use case interactor
        youtubeMatchUseCaseInteractor.execute(youtubeMatchInputData, gotoNextView);
    }
}
