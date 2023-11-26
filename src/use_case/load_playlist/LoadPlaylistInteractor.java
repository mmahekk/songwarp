package use_case.load_playlist;

import entity.CompletePlaylist;
import entity.Playlist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import extra_functions.CheckMultiplePlaylist;
import extra_functions.SplitFile;

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
    public void execute(LoadPlaylistInputData loadPlaylistInputData) {
        try {
            String filepath = loadPlaylistInputData.getFilePath();
            CheckMultiplePlaylist checker = new CheckMultiplePlaylist(filepath);
            if (checker.check()) {
                SplitFile splitter = new SplitFile(filepath);
                splitter.splitFile();
                LoadPlaylistOutputData CompletePlaylist = new LoadPlaylistOutputData();
                CompletePlaylist incompletePlaylist = loadPlaylistDataAccess.LoadCompletePlaylist("CompletePlaylist.json");
                CompletePlaylist.setCompletePlaylist(incompletePlaylist);
                if (Objects.equals(loadPlaylistDataAccess.Type("IncompletePlaylist.json"), "YoutubePlaylist")) {
                    YoutubePlaylist youtubePlaylist = loadPlaylistDataAccess.LoadYoutubePlaylist("IncompletePlaylist.json");
                    CompletePlaylist.setPlaylist(youtubePlaylist);
                }
                else if (Objects.equals(loadPlaylistDataAccess.Type("IncompletePlaylist.json"), "SpotifyPlaylist")) {
                    SpotifyPlaylist spotifyPlaylist = loadPlaylistDataAccess.LoadSpotifyPlaylist("IncompletePlaylist.json");
                    CompletePlaylist.setPlaylist(spotifyPlaylist);
                }
                else {
                    loadPlaylistPresenter.prepareFailView("F");
                }
                loadPlaylistPresenter.prepareSuccessView(CompletePlaylist);
            }
            else {
                Playlist playlist = loadPlaylistDataAccess.LoadPlaylist(loadPlaylistInputData.getFilePath());
                LoadPlaylistOutputData CompletePlaylist = new LoadPlaylistOutputData();
                CompletePlaylist.setPlaylist(playlist);
                loadPlaylistPresenter.prepareSuccessView(CompletePlaylist);
            }

        } catch (Exception e) {
            loadPlaylistPresenter.prepareFailView("Failed to load playlist");
        }
    }
}
