import data_access.YoutubeGetDataAccessObject;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import org.junit.Before;
import org.junit.Test;

import data_access.TempPlaylistDataAccessObject;
import interface_adapter.youtube_get.YoutubeGetController;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;

public class TestYoutubeGet {
    private YoutubeGetController controller;
    private YoutubeGetInteractor interactor;
    private TempPlaylistDataAccessObject fileWriter;
    private YoutubeGetDataAccessInterface dataAccessObject;
    private YoutubeGetOutputBoundary outputBoundary;

    private String testUrl;

    @Before
    public void setup() {
        fileWriter = new TempPlaylistDataAccessObject();
        dataAccessObject = new YoutubeGetDataAccessObject();
        outputBoundary = null;
        testUrl = "https://www.youtube.com/playlist?list=PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5";
        interactor = new YoutubeGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        controller = new YoutubeGetController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        controller.execute(testUrl);
    }
}
