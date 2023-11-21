package interface_adapter.view_traverse;

import interface_adapter.*;
import use_case.save_playlist.SavePlaylistOutputData;
import use_case.view_traverse.ViewTraverseOutputBoundary;
import use_case.view_traverse.ViewTraverseOutputData;

public class ViewTraversePresenter implements ViewTraverseOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel getPlaylistViewModel;

    public ViewTraversePresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.getPlaylistViewModel = getPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView() {
        GetPlaylistState getPlaylistState = getPlaylistViewModel.getState();
        getPlaylistState.setPlaylist(null);
        getPlaylistState.setIncompletePlaylist(null);
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
