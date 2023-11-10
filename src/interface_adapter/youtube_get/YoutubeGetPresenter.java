package interface_adapter.youtube_get;

import interface_adapter.ViewManagerModel;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import use_case.youtube_get.YoutubeGetOutputData;

public class YoutubeGetPresenter implements YoutubeGetOutputBoundary {
    private final YoutubeGetViewModel youtubeGetViewModel;
    private ViewManagerModel viewManagerModel;

    public YoutubeGetPresenter(ViewManagerModel viewManagerModel, YoutubeGetViewModel youtubeGetViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.youtubeGetViewModel = youtubeGetViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubeGetOutputData response) {
        YoutubeGetState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setPlaylist(response.getPlaylist());
        this.youtubeGetViewModel.setState(youtubeGetState);
        this.youtubeGetViewModel.firePropertyChanged();

        this.viewManagerModel.setActiveView(youtubeGetViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        YoutubeGetState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setError(error);
        youtubeGetViewModel.firePropertyChanged();
    }
}
