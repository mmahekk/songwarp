import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import entity.Playlist;
import entity.YoutubePlaylist;
import entity.YoutubeSong;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_match.YoutubeMatchController;
import interface_adapter.youtube_match.YoutubeMatchPresenter;
import org.junit.Before;
import org.junit.Test;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchInteractor;
import use_case.youtube_match.YoutubeMatchOutputBoundary;

public class TestYoutubeMatch {
    private YoutubeMatchController controller;
    private Playlist youtubePlaylist;

    @Before
    public void setup() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ProcessPlaylistViewModel viewModel = new ProcessPlaylistViewModel();
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        TempFileWriterDataAccessObject backupFileWriter = new TempFileWriterDataAccessObject("backup.json");
        YoutubeMatchDataAccessInterface dataAccessObject = new YoutubeMatchDataAccessObject();
        YoutubeMatchOutputBoundary outputBoundary = new YoutubeMatchPresenter(viewManagerModel, viewModel, new PutPlaylistViewModel());

        youtubePlaylist = fileWriter.readPlaylistJSON();
        YoutubeMatchInteractor interactor = new YoutubeMatchInteractor(dataAccessObject, fileWriter, backupFileWriter, outputBoundary);
        controller = new YoutubeMatchController(interactor);
    }

    @Test
    public void testPresenterBehavior() {
        if (youtubePlaylist instanceof YoutubePlaylist) {
            controller.execute((YoutubePlaylist) youtubePlaylist, false);
        } else {
            System.out.println("The test exited safely without running, since the playlist stored in temp.json was not a Youtube playlist.");
            System.out.println("To effectively test this, try running TestYoutubeGet first.");
        }
    }
}
