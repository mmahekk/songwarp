import data_access.APIs.YoutubeAPIAdapter;
import data_access.APIs.YoutubeAPIAdapterInterface;
import data_access.YoutubeGetDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import org.junit.Before;
import org.junit.Test;

import data_access.TempFileWriterDataAccessObject;
import interface_adapter.youtube_get.YoutubeGetController;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;

public class TestYoutubeGet {
    private YoutubeGetController controller;
    private String testUrl;
    private String testUrlLong;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GetPlaylistViewModel viewModel = new GetPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("youtubeGetOutput.json");
        YoutubeAPIAdapterInterface api = new YoutubeAPIAdapter();
        YoutubeGetDataAccessInterface dataAccessObject = new YoutubeGetDataAccessObject(api);
        YoutubeGetOutputBoundary outputBoundary = new YoutubeGetPresenter(viewManagerModel, viewModel, new ProcessPlaylistViewModel());
        testUrlLong = "https://www.youtube.com/playlist?list=PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5";
        testUrl = "https://www.youtube.com/playlist?list=PLQ6xshOf41NlhTT3bPQgcaOCn1wkwClya";
        YoutubeGetInteractor interactor = new YoutubeGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        controller = new YoutubeGetController(interactor);
    }

    @Test
    public void testYoutubeGet() {
        controller.execute(testUrl);
    }

    @Test
    public void testBadInput() {
        controller.execute("badUrlExample");
    }

    @Test
    public void testYoutubeGetForlongPlaylist() {
        controller.execute(testUrlLong);
    }
}
