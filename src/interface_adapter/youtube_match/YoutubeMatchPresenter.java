package interface_adapter.youtube_match;

import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.youtube_match.YoutubeMatchOutputBoundary;
import use_case.youtube_match.YoutubeMatchOutputData;

public class YoutubeMatchPresenter implements YoutubeMatchOutputBoundary {

    private final ProcessPlaylistViewModel youtubeMatchViewModel;
//    private final PutPlaylistViewModel putPlaylistViewModel;  // for switching views
    private ViewManagerModel viewManagerModel;

    public YoutubeMatchPresenter(ViewManagerModel viewManagerModel,
                               ProcessPlaylistViewModel youtubeMatchViewModel
                               //PutPlaylistViewModel putPlaylistViewModel
                               ) {
        this.viewManagerModel = viewManagerModel;
        this.youtubeMatchViewModel = youtubeMatchViewModel;
        // this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubeMatchOutputData youtubeMatchOutputData) {

    }

    @Override
    public void prepareFailView(String error) {

    }
}
