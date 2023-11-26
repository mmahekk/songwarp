package interface_adapter.load_playlist;

import use_case.load_playlist.LoadPlaylistInputBoundary;
import use_case.load_playlist.LoadPlaylistInputData;

public class LoadPlaylistController {
    final LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor;

    public LoadPlaylistController(LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor) {
        this.loadPlaylistUseCaseInteractor = loadPlaylistUseCaseInteractor;
    }

    public void execute(String filePath) {
        LoadPlaylistInputData loadPlaylistInputData = new LoadPlaylistInputData(filePath);

        loadPlaylistUseCaseInteractor.execute(loadPlaylistInputData);
    }
}
