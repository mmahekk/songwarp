import data_access.SavePlaylistDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.save_playlist.SavePlaylistPresenter;
import interface_adapter.save_playlist.SavePlaylistViewModel;
import interface_adapter.save_playlist.SavePlaylistState;
import interface_adapter.ViewManagerModel;
import use_case.save_playlist.SavePlaylistInteractor;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.save_playlist.SavePlaylistInputData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class TestSavePlaylist {
    private SavePlaylistController controller;
    private SavePlaylistInteractor savePlaylistInteractor;
    private Playlist mainPlaylist;
    private Playlist incompletePlaylist;

    @Before
    public void setup() {
        // Set up the use case
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        SavePlaylistViewModel viewModel = new SavePlaylistViewModel();
        PutPlaylistViewModel putPlaylistViewModel = new PutPlaylistViewModel();
        SavePlaylistDataAccessObject dataAccessObject = new SavePlaylistDataAccessObject();
        SavePlaylistOutputBoundary outputBoundary = new SavePlaylistPresenter(viewManagerModel, viewModel, putPlaylistViewModel);
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");

        // Create a sample playlist for testing
        mainPlaylist = new CompletePlaylist("Test Playlist", "Test Genre", "YoutubeID",
                "SpotifyID");

        // Add a song to mainPlaylist
        mainPlaylist.addSong(new CompleteSong("Song 1", "Artist 1", "SpotifyID1",
                "YoutubeID1", "2023-01-01",
                "Title 1", "Channel 1", 180));

        // Create incomplete playlist for testing
        incompletePlaylist = new CompletePlaylist("Incomplete Playlist", "Incomplete Genre",
                "IncompleteYoutubeID", "IncompleteSpotifyID");

        // Add a song to incompletePlaylist
        incompletePlaylist.addSong(new CompleteSong("Incomplete Song", "Incomplete Artist",
                "IncompleteSpotifyID", "IncompleteYoutubeID", "2023-01-02",
                "Incomplete Title", "Incomplete Channel", 200));

        savePlaylistInteractor = new SavePlaylistInteractor(dataAccessObject, fileWriter, outputBoundary);
        controller = new SavePlaylistController(savePlaylistInteractor);
    }

    @Test
    public void testSavePlaylist() {
        // Execute the save playlist use case
        // Note, the file should be removed manually, I will work on a teardown to figure out how to delete files after
        // testing
        savePlaylistInteractor.execute(new SavePlaylistInputData(mainPlaylist, "testFilePath", incompletePlaylist));
    }
}