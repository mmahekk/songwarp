package interface_adapter.spotify_get;


import interface_adapter.*;
import interface_adapter.youtube_get.YoutubeGetState;
import use_case.spotify_get.SpotifyGetOutputBoundary;
import use_case.spotify_get.SpotifyGetOutputData;


public class SpotifyGetPresenter implements SpotifyGetOutputBoundary {

    private final GetPlaylistViewModel spotifyGetViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;  // for switching views
    private ViewManagerModel viewManagerModel;


    public SpotifyGetPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel, ProcessPlaylistViewModel processPlaylistViewModel){
        this.viewManagerModel = viewManagerModel;
        this.spotifyGetViewModel = getPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(SpotifyGetOutputData response){
        GetPlaylistState spotifyGetState = spotifyGetViewModel.getState();
        spotifyGetState.setPlaylist(response.getPlaylist());
        spotifyGetState.setError(null);
        this.spotifyGetViewModel.setState(spotifyGetState);
        this.spotifyGetViewModel.firePropertyChanged();

        ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
        processPlaylistState.setPlaylist(response.getPlaylist());
        this.processPlaylistViewModel.setState(processPlaylistState);
        this.processPlaylistViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(processPlaylistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState spotifyGetState = spotifyGetViewModel.getState();
        spotifyGetState.setError(error);
        spotifyGetViewModel.firePropertyChanged();
    }


}
