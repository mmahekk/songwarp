package use_case.spotify_match;

import entity.CompletePlaylist;
import entity.YoutubePlaylist;
import use_case.youtube_match.YoutubeMatchOutputData;

public interface SpotifyMatchOutputBoundary {

    void prepareSuccessView(YoutubeMatchOutputData youtubeMatchOutputData, Boolean gotoNextView);

    void prepareFailView(String error);

    void failSaveExit(YoutubePlaylist playlist, CompletePlaylist matchedPlaylist, String error);

}
