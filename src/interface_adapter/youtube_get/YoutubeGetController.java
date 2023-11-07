package interface_adapter.youtube_get;

import use_case.youtube_get.YoutubeGetInputData;
import use_case.youtube_get.YoutubeGetInputBoundary;

public class YoutubeGetController {

    final YoutubeGetInputBoundary youtubeGetUseCaseInteractor;
    public YoutubeGetController(YoutubeGetInputBoundary youtubeGetUseCaseInteractor) {
        this.youtubeGetUseCaseInteractor = youtubeGetUseCaseInteractor;
    }

    public void execute(String url) {
        YoutubeGetInputData youtubeGetInputData = new YoutubeGetInputData(url);

        //invoke the use case interactor
        youtubeGetUseCaseInteractor.execute(youtubeGetInputData);
    }
}
