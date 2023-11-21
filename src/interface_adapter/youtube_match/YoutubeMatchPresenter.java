package interface_adapter.youtube_match;

import entity.CompletePlaylist;
import entity.Playlist;
import entity.YoutubePlaylist;
import interface_adapter.*;
import use_case.youtube_match.YoutubeMatchOutputBoundary;
import use_case.youtube_match.YoutubeMatchOutputData;

public class YoutubeMatchPresenter implements YoutubeMatchOutputBoundary {

    private final ProcessPlaylistViewModel youtubeMatchViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;  // for switching views
    private ViewManagerModel viewManagerModel;

    public YoutubeMatchPresenter(ViewManagerModel viewManagerModel,
                               ProcessPlaylistViewModel youtubeMatchViewModel,
                               PutPlaylistViewModel putPlaylistViewModel
                               ) {
        this.viewManagerModel = viewManagerModel;
        this.youtubeMatchViewModel = youtubeMatchViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(YoutubeMatchOutputData response, Boolean gotoNextView) {
        if (gotoNextView) {
            ProcessPlaylistState youtubeMatchState = youtubeMatchViewModel.getState();
            youtubeMatchState.setPlaylist(response.getPlaylist());
            youtubeMatchState.setError(null);
            this.youtubeMatchViewModel.setState(youtubeMatchState);
            this.youtubeMatchViewModel.firePropertyChanged();

            PutPlaylistState putPlaylistState = putPlaylistViewModel.getState();
            putPlaylistState.setPlaylist(response.getPlaylist());
            this.putPlaylistViewModel.setState(putPlaylistState);
            this.putPlaylistViewModel.firePropertyChanged();

            this.viewManagerModel.setActiveView(putPlaylistViewModel.getViewName());
            this.viewManagerModel.firePropertyChanged();
        } else {
            ProcessPlaylistState youtubeMatchState = youtubeMatchViewModel.getState();
            youtubeMatchState.setPlaylist(response.getPlaylist());
            youtubeMatchState.setError(null);
            this.youtubeMatchViewModel.setState(youtubeMatchState);
            this.youtubeMatchViewModel.firePropertyChanged();
        }
    }

    @Override
    public void prepareFailView(String error) {
        ProcessPlaylistState youtubeGetState = youtubeMatchViewModel.getState();
        youtubeGetState.setError(error);
        youtubeMatchViewModel.firePropertyChanged();
    }

    @Override
    public void failSaveExit(YoutubePlaylist playlist, CompletePlaylist matchedPlaylist, String error) {
        ProcessPlaylistState youtubeMatchState = youtubeMatchViewModel.getState();
        youtubeMatchState.setPlaylist(playlist);
        youtubeMatchState.setIncompletePlaylist(matchedPlaylist);
        youtubeMatchState.setError(error);
        youtubeMatchViewModel.firePropertyChanged();
    }
}
