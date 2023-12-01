package interface_adapter.spotify_match;

import entity.CompletePlaylist;
import entity.YoutubePlaylist;
import use_case.spotify_match.SpotifyMatchOutputBoundary;
import use_case.youtube_match.YoutubeMatchOutputData;

public class SpotifyMatchPresenter implements SpotifyMatchOutputBoundary {
    @Override
    public void prepareSuccessView(YoutubeMatchOutputData youtubeMatchOutputData, Boolean gotoNextView) {

    }

    @Override
    public void prepareFailView(String error) {

    }

    @Override
    public void failSaveExit(YoutubePlaylist playlist, CompletePlaylist matchedPlaylist, String error) {

    }
}
