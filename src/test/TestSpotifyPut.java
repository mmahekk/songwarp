import data_access.SpotifyPutDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubePutDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_put.SpotifyPutController;
import interface_adapter.spotify_put.SpotifyPutPresenter;
import interface_adapter.youtube_put.YoutubePutController;
import interface_adapter.youtube_put.YoutubePutPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.spotify_put.SpotifyPutDataAccessInterface;
import use_case.spotify_put.SpotifyPutInteractor;
import use_case.spotify_put.SpotifyPutOutputBoundary;
import use_case.youtube_put.YoutubePutDataAccessInterface;
import use_case.youtube_put.YoutubePutInteractor;
import use_case.youtube_put.YoutubePutOutputBoundary;

public class TestSpotifyPut {
    private SpotifyPutController controller;
    private Playlist mainPlaylist;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        PutPlaylistViewModel viewModel = new PutPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        SpotifyPutDataAccessInterface dataAccessObject = new SpotifyPutDataAccessObject();
        SpotifyPutOutputBoundary presenter = new SpotifyPutPresenter(viewManagerModel, viewModel);

        mainPlaylist = fileWriter.readPlaylistJSON();

        SpotifyPutInteractor interactor = new SpotifyPutInteractor(dataAccessObject, presenter);
        controller = new SpotifyPutController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        if (mainPlaylist instanceof CompletePlaylist playlist) {
            controller.execute(playlist, "Test playlist");
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Complete playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet and then TestYoutubeMatch, or TestSpotifyGet then TestSpotifyMatch.");
        }
    }
}
