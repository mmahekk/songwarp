import data_access.APIs.SpotifyAPIAdapter;
import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.APIs.YoutubeAPIAdapter;
import data_access.APIs.YoutubeAPIAdapterInterface;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_match.SpotifyMatchController;
import interface_adapter.youtube_match.YoutubeMatchController;
import interface_adapter.youtube_match.YoutubeMatchPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchInteractor;
import use_case.youtube_match.YoutubeMatchOutputBoundary;

public class TestYoutubeMatch {
    private YoutubeMatchController controller;
    private Playlist mainPlaylist;
    private Playlist backup;
    private Playlist progress;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ProcessPlaylistViewModel viewModel = new ProcessPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("youtubeMatchOutputMain.json");
        TempFileWriterDataAccessObject backupFileWriter = new TempFileWriterDataAccessObject("youtubeMatchOutputBackup.json");
        TempFileWriterDataAccessObject reader = new TempFileWriterDataAccessObject("youtubeGetOutput.json");
        SpotifyAPIAdapterInterface api = new SpotifyAPIAdapter();
        YoutubeMatchDataAccessInterface dataAccessObject = new YoutubeMatchDataAccessObject(api);
        YoutubeMatchOutputBoundary outputBoundary = new YoutubeMatchPresenter(viewManagerModel, viewModel, new PutPlaylistViewModel());

        mainPlaylist = reader.readPlaylistJSON();
        backup = backupFileWriter.readPlaylistJSON();
        progress = fileWriter.readPlaylistJSON();

        YoutubeMatchInteractor interactor = new YoutubeMatchInteractor(dataAccessObject, fileWriter, backupFileWriter, outputBoundary);
        controller = new YoutubeMatchController(interactor);
    }

    @Test
    public void testYoutubeMatchNormal() {
        if (mainPlaylist instanceof YoutubePlaylist) {
            controller.execute((YoutubePlaylist) mainPlaylist, false);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first.");
        }
    }

    @Test
    public void testViewMover() {
        if (mainPlaylist instanceof YoutubePlaylist) {
            controller.execute((YoutubePlaylist) mainPlaylist, true);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first.");
        }
    }

    @Test
    public void testEarlyExitPresenter() {
        if (mainPlaylist instanceof YoutubePlaylist) {
            controller.execute((YoutubePlaylist) mainPlaylist, null, false, 3);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first. Also, ideally, the playlist should have 4 or more songs");
        }
    }

    @Test
    public void testHandlesIncompletePlaylist() {
        if (backup instanceof YoutubePlaylist && progress instanceof CompletePlaylist) {
            controller.execute((YoutubePlaylist) backup, progress, false);
        } else {
            System.out.println("The test exited safely without running, since eiher backup.json has no incomplete complete playlist or also temp.json is not a youtube playlist.");
            System.out.println("To effectively test this, try running testEarlyExitPresenter first, on a playlist of more than 3 songs (try YoutubeGet first if this still doesn't work).");
        }
    }
}
