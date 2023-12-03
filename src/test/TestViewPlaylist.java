import data_access.ViewPlaylistDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import entity.CompletePlaylist;
import entity.Playlist;
import interface_adapter.PutPlaylistState;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.view_playlist.ViewPlaylistController;
import interface_adapter.view_playlist.ViewPlaylistPresenter;
import use_case.view_playlist.ViewPlaylistInteractor;
import use_case.view_playlist.ViewPlaylistOutputBoundary;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestViewPlaylist {

    private ViewPlaylistController controller;

    private Playlist completePlaylist;

    private PutPlaylistViewModel viewPlaylistViewModel;

    @Before
    public void setup() {
        viewPlaylistViewModel = new PutPlaylistViewModel();

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

    // test that the information is being stored to the state correctly
    @Test
    public void testSongOutputCorrectness() {
        if (completePlaylist != null && completePlaylist instanceof CompletePlaylist) {
            controller.execute(completePlaylist);

            //Testing the state
            PutPlaylistState state = viewPlaylistViewModel.getState();
            assertNotNull(state);

            String outputTextView = state.getOutputTextView();
            assertNotNull(outputTextView);

            System.out.println(outputTextView);

            // Split the outputTextView into lines
            String[] lines = outputTextView.split("\n");

            String firstSongInfo = "Rolling in the Deep by Adele released 2011-01-24, " +
                    "YoutubeID: rYEDA3JcQqw, SpotifyID: 1c8gk2PeTE04A1pIDH9YMk, " +
                    "Youtube Title: Adele - Rolling in the Deep (Official Music Video), " +
                    "Youtube Channel Name: AdeleVEVO";
            assertEquals(firstSongInfo, lines[1]);
        }

        else {
            System.out.println("This was an empty playlist or not a complete playlist");
        }

    }

}

