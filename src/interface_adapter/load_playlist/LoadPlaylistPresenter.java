package interface_adapter.load_playlist;

import interface_adapter.*;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import use_case.load_playlist.LoadPlaylistOutputData;

public class LoadPlaylistPresenter implements LoadPlaylistOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel loadPlaylistViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;

    public LoadPlaylistPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel loadPlaylistViewModel,
                                 ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadPlaylistViewModel = loadPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }
    @Override
    public void prepareSuccessView(LoadPlaylistOutputData data) {
        GetPlaylistState loadPlaylistState = loadPlaylistViewModel.getState();
        loadPlaylistState.setError(null);
        this.loadPlaylistViewModel.setState(loadPlaylistState);
        this.loadPlaylistViewModel.firePropertyChanged();
        loadPlaylistState.setPlaylist(data.getPlaylist());

        if (data.getPlaylist() == null && data.getCompletePlaylist() != null) {
            PutPlaylistState putPlaylistState = putPlaylistViewModel.getState();
            putPlaylistState.setPlaylist(data.getCompletePlaylist());
            this.putPlaylistViewModel.setState(putPlaylistState);
            this.putPlaylistViewModel.firePropertyChanged();

            this.viewManagerModel.setActiveView(putPlaylistViewModel.getViewName());
            this.viewManagerModel.firePropertyChanged();
        } else {
            ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
            this.processPlaylistViewModel.setState(processPlaylistState);
            this.processPlaylistViewModel.firePropertyChanged();

            this.viewManagerModel.setActiveView(processPlaylistViewModel.getViewName());
            this.viewManagerModel.firePropertyChanged();
            if (data.getCompletePlaylist() != null) {
                loadPlaylistState.setIncompletePlaylist(data.getCompletePlaylist());
                processPlaylistState.setPlaylist(data.getPlaylist());
                processPlaylistState.setIncompletePlaylist(data.getCompletePlaylist());
            } else {
                processPlaylistState.setPlaylist(data.getPlaylist());
            }
        }
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState loadPlaylistGetState = loadPlaylistViewModel.getState();
        loadPlaylistGetState.setError(error);
        loadPlaylistViewModel.firePropertyChanged();
    }
}
