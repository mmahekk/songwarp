
import data_access.ViewPlaylistDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import entity.YoutubePlaylist;
import entity.SpotifyPlaylist;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.view_playlist.ViewPlaylistController;
import interface_adapter.view_playlist.ViewPlaylistPresenter;
import use_case.view_playlist.ViewPlaylistInteractor;
import use_case.view_playlist.ViewPlaylistOutputBoundary;
import use_case.view_playlist.ViewPlaylistInputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class TestViewPlaylist {

    private ViewPlaylistController controller;

    private Playlist completePlaylist;

    @Before
    public void setup() {
        PutPlaylistViewModel viewPlaylistViewModel = new PutPlaylistViewModel();
        ViewPlaylistDataAccessObject dataAccessObject = new ViewPlaylistDataAccessObject();
        ViewPlaylistOutputBoundary outputBoundary = new ViewPlaylistPresenter(viewPlaylistViewModel);
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");

        // Read playlists from JSON file
        completePlaylist = fileWriter.readPlaylistJSON();

        ViewPlaylistInteractor interactor = new ViewPlaylistInteractor(dataAccessObject, outputBoundary);

        controller = new ViewPlaylistController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        if (completePlaylist != null && completePlaylist instanceof CompletePlaylist) {
            controller.execute(completePlaylist);
        }

        else {
            System.out.println("This was an empty playlist or not a complete playlist");
        }

    }

}

