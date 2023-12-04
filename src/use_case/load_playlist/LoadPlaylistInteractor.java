package use_case.load_playlist;

import entity.CompletePlaylist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import utilities.CheckMultiplePlaylist;
import utilities.SplitFile;

import java.util.Objects;

public class LoadPlaylistInteractor implements LoadPlaylistInputBoundary {
    private final LoadPlaylistDataAccessInterface loadPlaylistDataAccess;
    private final LoadPlaylistOutputBoundary loadPlaylistPresenter;

    public LoadPlaylistInteractor(LoadPlaylistDataAccessInterface loadPlaylistDataAccess,
                                  LoadPlaylistOutputBoundary loadPlaylistPresenter) {
        this.loadPlaylistDataAccess = loadPlaylistDataAccess;
        this.loadPlaylistPresenter = loadPlaylistPresenter;
    }

    @Override
    public void execute() {
        try {
            String filepath = loadPlaylistDataAccess.GetFilePath();
            CheckMultiplePlaylist checker = new CheckMultiplePlaylist(filepath);
            if (checker.check()) {
                SplitFile splitter = new SplitFile(filepath);
                splitter.splitFile();
                LoadPlaylistOutputData completePlaylist = new LoadPlaylistOutputData();
                CompletePlaylist incompletePlaylist = loadPlaylistDataAccess.LoadCompletePlaylist("IncompletePlaylist.json");
                completePlaylist.setCompletePlaylist(incompletePlaylist);
                if (Objects.equals(loadPlaylistDataAccess.Type("CompletePlaylist.json"), "YoutubePlaylist")) {
                    YoutubePlaylist youtubePlaylist = loadPlaylistDataAccess.LoadYoutubePlaylist("CompletePlaylist.json");
                    completePlaylist.setPlaylist(youtubePlaylist);
                }
                else if (Objects.equals(loadPlaylistDataAccess.Type("CompletePlaylist.json"), "SpotifyPlaylist")) {
                    SpotifyPlaylist spotifyPlaylist = loadPlaylistDataAccess.LoadSpotifyPlaylist("CompletePlaylist.json");
                    completePlaylist.setPlaylist(spotifyPlaylist);
                } else {loadPlaylistPresenter.prepareFailView("Failed to load playlist");}
                loadPlaylistPresenter.prepareSuccessView(completePlaylist);
            } else {
                CompletePlaylist playlist = loadPlaylistDataAccess.LoadCompletePlaylist(filepath);
                LoadPlaylistOutputData completePlaylist = new LoadPlaylistOutputData();
                completePlaylist.setCompletePlaylist(playlist);
                loadPlaylistPresenter.prepareSuccessView(completePlaylist);
            }
        } catch (Exception e) {loadPlaylistPresenter.prepareFailView("Failed to load playlist");}
    }
}
