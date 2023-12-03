package interface_adapter.spotify_put;

import interface_adapter.PutPlaylistState;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.spotify_put.SpotifyPutOutputBoundary;
import use_case.spotify_put.SpotifyPutOutputData;
import use_case.youtube_put.YoutubePutOutputData;

public class SpotifyPutPresenter implements SpotifyPutOutputBoundary {
    private final PutPlaylistViewModel putPlaylistViewModel;  // for switching views
    private final ViewManagerModel viewManagerModel;

    public SpotifyPutPresenter(ViewManagerModel viewManagerModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(SpotifyPutOutputData response) {
        PutPlaylistState spotifyPutState = putPlaylistViewModel.getState();
        spotifyPutState.setPlaylist(response.getPlaylist());
        spotifyPutState.setError(response.getPlaylistYoutubeUrl());
        this.putPlaylistViewModel.setState(spotifyPutState);
        this.putPlaylistViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        PutPlaylistState spotifyPutState = putPlaylistViewModel.getState();
        spotifyPutState.setError(error);
        putPlaylistViewModel.firePropertyChanged();
    }
}
