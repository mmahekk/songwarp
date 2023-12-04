import data_access.APIs.YoutubeAPIAdapter;
import data_access.APIs.YoutubeAPIAdapterInterface;
import data_access.SpotifyPutDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_put.SpotifyPutController;
import interface_adapter.spotify_put.SpotifyPutPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.spotify_put.SpotifyPutDataAccessInterface;
import use_case.spotify_put.SpotifyPutInteractor;
import use_case.spotify_put.SpotifyPutOutputBoundary;

public class TestSpotifyPut {
    private SpotifyPutController controller;
    private Playlist mainPlaylist;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        PutPlaylistViewModel viewModel = new PutPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("spotifyMatchOutputMain.json");
        YoutubeAPIAdapterInterface api = new YoutubeAPIAdapter();
        SpotifyPutDataAccessInterface dataAccessObject = new SpotifyPutDataAccessObject(api);
        SpotifyPutOutputBoundary presenter = new SpotifyPutPresenter(viewManagerModel, viewModel);

        mainPlaylist = fileWriter.readPlaylistJSON();

        SpotifyPutInteractor interactor = new SpotifyPutInteractor(dataAccessObject, presenter);
        controller = new SpotifyPutController(interactor);
    }

    @Test
    public void testSpotifyPut() {
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
