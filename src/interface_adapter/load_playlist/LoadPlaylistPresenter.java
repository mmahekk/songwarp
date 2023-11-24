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
    public void prepareSuccessView(LoadPlaylistOutputData playlist) {
        GetPlaylistState loadPlaylistState = loadPlaylistViewModel.getState();
        loadPlaylistState.setError(null);
        this.loadPlaylistViewModel.setState(loadPlaylistState);
        this.loadPlaylistViewModel.firePropertyChanged();

        ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
        this.processPlaylistViewModel.setState(processPlaylistState);
        this.processPlaylistViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(processPlaylistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
        if (playlist.getCompletePlaylist() != null) {
            loadPlaylistState.setPlaylist(playlist.getPlaylist());
            loadPlaylistState.setIncompletePlaylist(playlist.getCompletePlaylist());

            processPlaylistState.setPlaylist(playlist.getPlaylist());
            processPlaylistState.setIncompletePlaylist(playlist.getCompletePlaylist());
        }
        else {
            loadPlaylistState.setPlaylist(playlist.getPlaylist());

            processPlaylistState.setPlaylist(playlist.getPlaylist());
        }
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState loadPlaylistGetState = loadPlaylistViewModel.getState();
        loadPlaylistGetState.setError(error);
        loadPlaylistViewModel.firePropertyChanged();
    }
}
