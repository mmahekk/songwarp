import data_access.SpotifyMatchDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import entity.SpotifyPlaylist;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_match.SpotifyMatchController;
import interface_adapter.spotify_match.SpotifyMatchPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.spotify_match.SpotifyMatchDataAccessInterface;
import use_case.spotify_match.SpotifyMatchInteractor;
import use_case.spotify_match.SpotifyMatchOutputBoundary;

public class TestSpotifyMatch {
    private SpotifyMatchController controller;
    private Playlist mainPlaylist;
    private Playlist otherPlaylist;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ProcessPlaylistViewModel viewModel = new ProcessPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        TempFileWriterDataAccessObject backupFileWriter = new TempFileWriterDataAccessObject("backup.json");
        SpotifyMatchDataAccessInterface dataAccessObject = new SpotifyMatchDataAccessObject();
        SpotifyMatchOutputBoundary outputBoundary = new SpotifyMatchPresenter(viewManagerModel, viewModel, new PutPlaylistViewModel());

        mainPlaylist = fileWriter.readPlaylistJSON();
        otherPlaylist = backupFileWriter.readPlaylistJSON();

        SpotifyMatchInteractor interactor = new SpotifyMatchInteractor(dataAccessObject, fileWriter, backupFileWriter, outputBoundary);
        controller = new SpotifyMatchController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        if (mainPlaylist instanceof SpotifyPlaylist) {
            controller.execute((SpotifyPlaylist) mainPlaylist, false);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first.");
        }
    }

    @Test
    public void testEarlyExitPresenter() {
        if (mainPlaylist instanceof SpotifyPlaylist) {
            controller.execute((SpotifyPlaylist) mainPlaylist, null, false, 3);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first. Also, ideally, the playlist should have 4 or more songs");
        }
    }

    @Test
    public void testHandlesIncompletePlaylist() {
        if (otherPlaylist instanceof SpotifyPlaylist && mainPlaylist instanceof CompletePlaylist) {
            controller.execute((SpotifyPlaylist) otherPlaylist, mainPlaylist, false);
        } else {
            System.out.println("The test exited safely without running, since eiher backup.json has no incomplete complete playlist or also temp.json is not a youtube playlist.");
            System.out.println("To effectively test this, try running testEarlyExitPresenter first, on a playlist of more than 3 songs (try YoutubeGet first if this still doesn't work).");
        }
    }
}
