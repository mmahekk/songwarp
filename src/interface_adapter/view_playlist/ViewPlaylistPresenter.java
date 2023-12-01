package interface_adapter.view_playlist;

import use_case.view_playlist.ViewPlaylistOutputBoundary;
import use_case.view_playlist.ViewPlaylistOutputData;
import interface_adapter.*;

import java.util.List;

public class ViewPlaylistPresenter implements ViewPlaylistOutputBoundary {

    private final PutPlaylistViewModel viewPlaylistViewModel;

    public ViewPlaylistPresenter(PutPlaylistViewModel viewPlaylistViewModel) {

        this.viewPlaylistViewModel = viewPlaylistViewModel;

    }

    @Override
    public void prepareSuccessView(ViewPlaylistOutputData viewPlaylistOutputData) {

        PutPlaylistState viewPlaylistState = viewPlaylistViewModel.getState();
        viewPlaylistState.setError(null);
        List<String> playlistText = viewPlaylistOutputData.getPlaylistText();
        viewPlaylistState.setOutputTextView(String.join("\n", playlistText));
        this.viewPlaylistViewModel.setState(viewPlaylistState);
        this.viewPlaylistViewModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {
        PutPlaylistState viewPlaylistState = viewPlaylistViewModel.getState();
        viewPlaylistState.setError(error);
        this.viewPlaylistViewModel.firePropertyChanged();

    }

}
