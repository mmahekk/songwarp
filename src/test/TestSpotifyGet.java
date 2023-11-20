import data_access.SpotifyGetDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import org.junit.Before;
import org.junit.Test;

import data_access.TempFileWriterDataAccessObject;
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
        GetPlaylistViewModel viewModel = new GetPlaylistViewModel();
        ProcessPlaylistViewModel nextViewModel = new ProcessPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        SpotifyGetDataAccessInterface dataAccessObject = new SpotifyGetDataAccessObject();
        SpotifyGetOutputBoundary outputBoundary = new SpotifyGetPresenter(viewManagerModel, viewModel, nextViewModel);
        testUrl = "https://open.spotify.com/playlist/6BHIpWRDOIznQi7HiVvlpZ";
        SpotifyGetInteractor interactor = new SpotifyGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        controller = new SpotifyGetController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        controller.execute(testUrl);
    }
}