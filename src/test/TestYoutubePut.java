import data_access.APIs.SpotifyAPIAdapter;
import data_access.APIs.SpotifyAPIAdapterInterface;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubePutDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_put.YoutubePutController;
import interface_adapter.youtube_put.YoutubePutPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.youtube_put.YoutubePutDataAccessInterface;
import use_case.youtube_put.YoutubePutInteractor;
import use_case.youtube_put.YoutubePutOutputBoundary;

public class TestYoutubePut {
    private YoutubePutController controller;
    private Playlist mainPlaylist;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        PutPlaylistViewModel viewModel = new PutPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("youtubeMatchOutputMain.json");
        SpotifyAPIAdapterInterface api = new SpotifyAPIAdapter();
        YoutubePutDataAccessInterface dataAccessObject = new YoutubePutDataAccessObject(api);
        YoutubePutOutputBoundary outputBoundary = new YoutubePutPresenter(viewManagerModel, viewModel);

        mainPlaylist = fileWriter.readPlaylistJSON();

        YoutubePutInteractor interactor = new YoutubePutInteractor(dataAccessObject, outputBoundary);
        controller = new YoutubePutController(interactor);
    }

    @Test
    public void testYoutubePut() {
        if (mainPlaylist instanceof CompletePlaylist playlist) {
            controller.execute(playlist, "Test playlist");
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Complete playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet and then TestYoutubeMatch, or TestSpotifyGet then TestSpotifyMatch.");
        }
    }

    @Test
    public void testNoName() {
        if (mainPlaylist instanceof CompletePlaylist playlist) {
            controller.execute(playlist, "");
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Complete playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet and then TestYoutubeMatch, or TestSpotifyGet then TestSpotifyMatch.");
        }
    }
}
