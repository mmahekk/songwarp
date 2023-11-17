import data_access.SpotifyGetDataAccessObject;
import data_access.YoutubeGetDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import interface_adapter.spotify_get.SpotifyGetViewModel;
import org.junit.Before;
import org.junit.Test;

import data_access.TempPlaylistDataAccessObject;
import interface_adapter.spotify_get.SpotifyGetController;
import use_case.spotify_get.SpotifyGetDataAccessInterface;
import use_case.spotify_get.SpotifyGetInteractor;
import use_case.spotify_get.SpotifyGetOutputBoundary;

public class TestSpotifyGet {
    private SpotifyGetController controller;
    private String testUrl;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SpotifyGetViewModel viewModel = new SpotifyGetViewModel();
        TempPlaylistDataAccessObject fileWriter = new TempPlaylistDataAccessObject();
        SpotifyGetDataAccessInterface dataAccessObject = new SpotifyGetDataAccessObject();
        SpotifyGetOutputBoundary outputBoundary = new SpotifyGetPresenter(viewManagerModel, viewModel);
        testUrl = "https://www.youtube.com/playlist?list=PLQ6xshOf41Nk3Ff_D9GyOpVCBZ7zc8NN5";
        SpotifyGetInteractor interactor = new SpotifyGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        controller = new SpotifyGetController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        controller.execute(testUrl);
    }
}