package interface_adapter.load_playlist;

import use_case.load_playlist.LoadPlaylistInputBoundary;
import use_case.load_playlist.LoadPlaylistInputData;

public class LoadPlaylistController {
    final LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor;

    public LoadPlaylistController(LoadPlaylistInputBoundary loadPlaylistUseCaseInteractor) {
        this.loadPlaylistUseCaseInteractor = loadPlaylistUseCaseInteractor;
    }

    public void execute(String filePath) {
        //TODO: change the next line after figuring out how to get input json file
        LoadPlaylistInputData loadPlaylistInputData = new LoadPlaylistInputData(filePath); //using temp.json as placeholder

        loadPlaylistUseCaseInteractor.execute(loadPlaylistInputData);
    }
}
