package interface_adapter.youtube_get;

import interface_adapter.*;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import use_case.youtube_get.YoutubeGetOutputData;

public class YoutubeGetPresenter implements YoutubeGetOutputBoundary {
    private final GetPlaylistViewModel youtubeGetViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;  // for switching views
    private ViewManagerModel viewManagerModel;

    public YoutubeGetPresenter(ViewManagerModel viewManagerModel,
                               GetPlaylistViewModel youtubeGetViewModel,
                               ProcessPlaylistViewModel processPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.youtubeGetViewModel = youtubeGetViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubeGetOutputData response) {
        GetPlaylistState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setPlaylist(response.getPlaylist());
        youtubeGetState.setError(null);
        this.youtubeGetViewModel.setState(youtubeGetState);
        this.youtubeGetViewModel.firePropertyChanged();

        ProcessPlaylistState processPlaylistState = processPlaylistViewModel.getState();
        processPlaylistState.setPlaylist(response.getPlaylist());
        this.processPlaylistViewModel.setState(processPlaylistState);
        this.processPlaylistViewModel.firePropertyChanged();


        this.viewManagerModel.setActiveView(processPlaylistViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        GetPlaylistState youtubeGetState = youtubeGetViewModel.getState();
        youtubeGetState.setError(error);
        youtubeGetViewModel.firePropertyChanged();
    }
}
