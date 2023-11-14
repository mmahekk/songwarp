package interface_adapter.youtube_get;

import interface_adapter.GetPlaylistState;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import use_case.youtube_get.YoutubeGetOutputData;

public class YoutubeGetPresenter implements YoutubeGetOutputBoundary {
    private final GetPlaylistViewModel youtubeGetViewModel;
    private ViewManagerModel viewManagerModel;

    public YoutubeGetPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel youtubeGetViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.youtubeGetViewModel = youtubeGetViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubeGetOutputData response) {
        GetPlaylistState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setPlaylist(response.getPlaylist());
        youtubeGetState.setError(null);
        this.youtubeGetViewModel.setState(youtubeGetState);
        this.youtubeGetViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(youtubeGetViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setError(error);
        youtubeGetViewModel.firePropertyChanged();
    }
}
