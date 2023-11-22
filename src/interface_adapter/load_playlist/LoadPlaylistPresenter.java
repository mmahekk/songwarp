package interface_adapter.load_playlist;

import interface_adapter.*;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import use_case.load_playlist.LoadPlaylistOutputData;

public class LoadPlaylistPresenter implements LoadPlaylistOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel loadPlaylistViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;

    public LoadPlaylistPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel loadPlaylistViewModel,
                                 ProcessPlaylistViewModel processPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadPlaylistViewModel = loadPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
    }
    @Override
    public void prepareSuccessView(LoadPlaylistOutputData outputData) {
        GetPlaylistState loadPlaylistState = loadPlaylistViewModel.getState();
        loadPlaylistState.setPlaylist(outputData.getPlaylist());
        loadPlaylistState.setError(null);
        this.loadPlaylistViewModel.setState(loadPlaylistState);
        this.loadPlaylistViewModel.firePropertyChanged();

        ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
        processPlaylistState.setPlaylist(outputData.getPlaylist());
        this.processPlaylistViewModel.setState(processPlaylistState);
        this.processPlaylistViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(processPlaylistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState loadPlaylistGetState = loadPlaylistViewModel.getState();
        loadPlaylistGetState.setError(error);
        loadPlaylistViewModel.firePropertyChanged();
    }
}
