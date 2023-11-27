package interface_adapter.load_playlist;

import use_case.load_playlist.LoadPlaylistInputBoundary;

public class LoadPlaylistController {
    final LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor;

    public LoadPlaylistController(LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor) {
        this.loadPlaylistUseCaseInteractor = loadPlaylistUseCaseInteractor;
    }

    public void execute() {

        loadPlaylistUseCaseInteractor.execute();
    }
}
