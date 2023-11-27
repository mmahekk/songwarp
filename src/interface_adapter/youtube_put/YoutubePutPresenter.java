package interface_adapter.youtube_put;

import interface_adapter.PutPlaylistState;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.youtube_put.YoutubePutOutputBoundary;
import use_case.youtube_put.YoutubePutOutputData;

public class YoutubePutPresenter implements YoutubePutOutputBoundary {
    private final PutPlaylistViewModel putPlaylistViewModel;  // for switching views
    private final ViewManagerModel viewManagerModel;

    public YoutubePutPresenter(ViewManagerModel viewManagerModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubePutOutputData response) {
        PutPlaylistState youtubePutState = putPlaylistViewModel.getState();
        youtubePutState.setPlaylist(response.getPlaylist());
        youtubePutState.setError(response.getPlaylistSpotifyUrl());
        this.putPlaylistViewModel.setState(youtubePutState);
        this.putPlaylistViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        PutPlaylistState youtubePutState = putPlaylistViewModel.getState();
        youtubePutState.setError(error);
        putPlaylistViewModel.firePropertyChanged();
    }
}
