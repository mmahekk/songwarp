package use_case.load_playlist;

import data_access.TempFileWriterDataAccessObject;
import entity.Playlist;

public class LoadPlaylistInteractor implements LoadPlaylistInputBoundary {
    private final LoadPlaylistDataAccessInterface loadPlaylistDataAccess;
    private final TempFileWriterDataAccessObject fileWriter;
    private final LoadPlaylistOutputBoundary loadPlaylistPresenter;

    public LoadPlaylistInteractor(LoadPlaylistDataAccessInterface loadPlaylistDataAccess,
                                  TempFileWriterDataAccessObject fileWriter,
                                  LoadPlaylistOutputBoundary loadPlaylistPresenter) {
        this.loadPlaylistDataAccess = loadPlaylistDataAccess;
        this.fileWriter = fileWriter;
        this.loadPlaylistPresenter = loadPlaylistPresenter;
    }


    @Override
    public void execute(LoadPlaylistInputData loadPlaylistInputData) {
        try {
            Playlist playlist = loadPlaylistDataAccess.LoadPlaylist();

            LoadPlaylistOutputData outputData = new LoadPlaylistOutputData(playlist);
            loadPlaylistPresenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            loadPlaylistPresenter.prepareFailView("Failed to load playlist");
        }
    }
}
