package interface_adapter.spotify_match;

import entity.CompletePlaylist;
import entity.SpotifyPlaylist;
import interface_adapter.*;
import use_case.spotify_match.SpotifyMatchOutputBoundary;
import use_case.spotify_match.SpotifyMatchOutputData;

public class SpotifyMatchPresenter implements SpotifyMatchOutputBoundary {
    private final ProcessPlaylistViewModel spotifyMatchViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;  // for switching views
    private ViewManagerModel viewManagerModel;

    public SpotifyMatchPresenter(ViewManagerModel viewManagerModel,
                                 ProcessPlaylistViewModel spotifyMatchViewModel,
                                 PutPlaylistViewModel putPlaylistViewModel
    ) {
        this.viewManagerModel = viewManagerModel;
        this.spotifyMatchViewModel = spotifyMatchViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(SpotifyMatchOutputData response, Boolean gotoNextView) {
        if (gotoNextView) {
            ProcessPlaylistState spotifyMatchState = spotifyMatchViewModel.getState();
            spotifyMatchState.setPlaylist(response.getPlaylist());
            spotifyMatchState.setError(null);
            this.spotifyMatchViewModel.setState(spotifyMatchState);
            this.spotifyMatchViewModel.firePropertyChanged();

            PutPlaylistState putPlaylistState = putPlaylistViewModel.getState();
            putPlaylistState.setPlaylist(response.getPlaylist());
            this.putPlaylistViewModel.setState(putPlaylistState);
            this.putPlaylistViewModel.firePropertyChanged();

            this.viewManagerModel.setActiveView(putPlaylistViewModel.getViewName());
            this.viewManagerModel.firePropertyChanged();
        } else {
            ProcessPlaylistState spotifyMatchState = spotifyMatchViewModel.getState();
            spotifyMatchState.setPlaylist(response.getPlaylist());
            spotifyMatchState.setError(null);
            this.spotifyMatchViewModel.setState(spotifyMatchState);
            this.spotifyMatchViewModel.firePropertyChanged();
        }
    }

    @Override
    public void prepareFailView(String error) {
        ProcessPlaylistState spotifyPutState = spotifyMatchViewModel.getState();
        spotifyPutState.setError(error);
        spotifyMatchViewModel.firePropertyChanged();
    }

    @Override
    public void failSaveExit(SpotifyPlaylist playlist, CompletePlaylist matchedPlaylist, String error) {
        ProcessPlaylistState spotifyMatchState = spotifyMatchViewModel.getState();
        spotifyMatchState.setPlaylist(playlist);
        spotifyMatchState.setIncompletePlaylist(matchedPlaylist);
        spotifyMatchState.setError(error);
        spotifyMatchState.setForcedToSave(true);
        spotifyMatchViewModel.setState(spotifyMatchState);
        spotifyMatchViewModel.firePropertyChanged();
    }
}
