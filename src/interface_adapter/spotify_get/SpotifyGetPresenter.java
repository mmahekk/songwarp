package interface_adapter.spotify_get;


import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_get.YoutubeGetState;
import use_case.spotify_get.SpotifyGetOutputBoundary;
import use_case.spotify_get.SpotifyGetOutputData;


public class SpotifyGetPresenter implements SpotifyGetOutputBoundary {

    private final SpotifyGetViewModel spotifyGetViewModel;
    private ViewManagerModel viewManagerModel;


    public SpotifyGetPresenter(ViewManagerModel viewManagerModel, SpotifyGetViewModel spotifyGetViewModel){
        this.viewManagerModel = viewManagerModel;
        this.spotifyGetViewModel = spotifyGetViewModel;
    }

    @Override
    public void prepareSuccessView(SpotifyGetOutputData response){

        SpotifyGetState spotifyGetState = spotifyGetViewModel.getState();
        spotifyGetState.setPlaylist(response.getPlaylist());
        this.spotifyGetViewModel.setState(spotifyGetState);
        this.spotifyGetViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(spotifyGetViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();



    }

    @Override
    public void prepareFailView(String error) {
        SpotifyGetState spotifyGetState = spotifyGetViewModel.getState();
        spotifyGetState.setPlaylistGetError(error);
        spotifyGetViewModel.firePropertyChanged();
    }


}
