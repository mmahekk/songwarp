import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeGetDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import data_access.YoutubePutDataAccessObject;
import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import entity.YoutubePlaylist;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import interface_adapter.youtube_match.YoutubeMatchPresenter;
import interface_adapter.youtube_put.YoutubePutController;
import interface_adapter.youtube_put.YoutubePutPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchOutputBoundary;
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
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        YoutubePutDataAccessInterface dataAccessObject = new YoutubePutDataAccessObject();
        YoutubePutOutputBoundary outputBoundary = new YoutubePutPresenter(viewManagerModel, viewModel);

        mainPlaylist = fileWriter.readPlaylistJSON();

        YoutubePutInteractor interactor = new YoutubePutInteractor(dataAccessObject, outputBoundary);
        controller = new YoutubePutController(interactor);
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
