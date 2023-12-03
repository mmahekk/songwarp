import data_access.LoadPlaylistDataAccessObject;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;
import utilities.SplitFile;

import org.junit.Before;
import org.junit.Test;
public class TestLoadPlaylist {
    private LoadPlaylistDataAccessInterface dataAccessObject;
    private SplitFile fileSplitter;

    @Before
    public void setup() {
        this.dataAccessObject = new LoadPlaylistDataAccessObject();
        String unfinishedPlaylistFile = "temp_jsons/UnfinishedPlaylist.txt";
        this.fileSplitter = new SplitFile(unfinishedPlaylistFile);
    }
    @Test
    public void TestSplitFile() {
        fileSplitter.splitFile();
    }
    @Test
    public void TestWithUnfinishedPlaylist() {
        fileSplitter.splitFile();
        dataAccessObject.LoadYoutubePlaylist("CompletePlaylist.json");
        dataAccessObject.LoadCompletePlaylist("IncompletePlaylist.json");
    }
    @Test
    public void TestWithCompletedPlaylist() {
        String completedPlaylistFile = "temp_jsons/CompletePlaylist.SWsave";
        dataAccessObject.LoadCompletePlaylist(completedPlaylistFile);
    }
}
