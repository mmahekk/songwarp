import data_access.LoadPlaylistDataAccessObject;
import interface_adapter.*;
import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.load_playlist.LoadPlaylistPresenter;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;
import use_case.load_playlist.LoadPlaylistInputBoundary;
import use_case.load_playlist.LoadPlaylistInteractor;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import utilities.SplitFile;

import org.junit.Before;
import org.junit.Test;

public class TestLoadPlaylist {
    private LoadPlaylistDataAccessInterface dataAccessObject;
    private SplitFile fileSplitter;

    @Before
    public void setup() {
        this.dataAccessObject = new LoadPlaylistDataAccessObject();
        String unfinishedPlaylistFile = "temp_jsons/testFilePath2.SWsave";
        this.fileSplitter = new SplitFile(unfinishedPlaylistFile);
    }
    @Test
    public void TestSplitFile() {
        fileSplitter.splitFile();
    }
    @Test
    public void TestWithUnfinishedPlaylist() {
        fileSplitter.splitFile();
        dataAccessObject.LoadYoutubePlaylist("CompletePlaylist.json");
        dataAccessObject.LoadCompletePlaylist("IncompletePlaylist.json");
    }

    @Test
    public void TestWithUnfinishedSpotify() {
        String completedPlaylistFile = "temp_jsons/spotifyInProgress.SWsave";
        dataAccessObject.LoadSpotifyPlaylist(completedPlaylistFile);
    }

    @Test
    public void TestWithCompletedPlaylist() {
        String completedPlaylistFile = "temp_jsons/test list.SWsave";
        dataAccessObject.LoadCompletePlaylist(completedPlaylistFile);
    }

    @Test
    public void TestaskUserToLoad() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GetPlaylistViewModel getPlaylistViewModel = new GetPlaylistViewModel();
        ProcessPlaylistViewModel processPlaylistViewModel = new ProcessPlaylistViewModel();
        PutPlaylistViewModel putPlaylistViewModel = new PutPlaylistViewModel();
        LoadPlaylistOutputBoundary loadPlaylistOutputBoundary = new LoadPlaylistPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
        LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject = new LoadPlaylistDataAccessObject();
        LoadPlaylistInputBoundary loadPlaylistInteractor = new LoadPlaylistInteractor(loadPlaylistDataAccessObject, loadPlaylistOutputBoundary);

        LoadPlaylistController controller = new LoadPlaylistController(loadPlaylistInteractor);
        controller.execute();
    }

    @Test
    public void TestaskUserToLoadAnUnfinished() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        GetPlaylistViewModel getPlaylistViewModel = new GetPlaylistViewModel();
        ProcessPlaylistViewModel processPlaylistViewModel = new ProcessPlaylistViewModel();
        PutPlaylistViewModel putPlaylistViewModel = new PutPlaylistViewModel();
        LoadPlaylistOutputBoundary loadPlaylistOutputBoundary = new LoadPlaylistPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
        LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject = new LoadPlaylistDataAccessObject();
        LoadPlaylistInputBoundary loadPlaylistInteractor = new LoadPlaylistInteractor(loadPlaylistDataAccessObject, loadPlaylistOutputBoundary);

        LoadPlaylistController controller = new LoadPlaylistController(loadPlaylistInteractor);
        controller.execute();
    }
}
