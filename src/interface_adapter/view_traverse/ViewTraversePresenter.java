package interface_adapter.view_traverse;

import interface_adapter.*;
import use_case.view_traverse.ViewTraverseOutputBoundary;

public class ViewTraversePresenter implements ViewTraverseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;

    public ViewTraversePresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel, ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView() {
        GetPlaylistState getPlaylistState = getPlaylistViewModel.getState();
        ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
        PutPlaylistState putPlaylistState = putPlaylistViewModel.getState();
        getPlaylistState.setPlaylist(null);
        getPlaylistState.setIncompletePlaylist(null);
        getPlaylistState.setError(null);

        processPlaylistState.setPlaylist(null);
        processPlaylistState.setIncompletePlaylist(null);
        processPlaylistState.setError(null);

        putPlaylistState.setPlaylist(null);
        putPlaylistState.setIncompletePlaylist(null);
        putPlaylistState.setError(null);

        getPlaylistState.setError("Restarting program");
        this.getPlaylistViewModel.setState(getPlaylistState);
        this.getPlaylistViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(getPlaylistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // you can never have a fail view traverse
    }
}
