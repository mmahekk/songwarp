package use_case.youtube_match;

import entity.CompletePlaylist;
import entity.YoutubePlaylist;
import use_case.youtube_get.YoutubeGetOutputData;

public interface YoutubeMatchOutputBoundary {
    void prepareSuccessView(YoutubeMatchOutputData youtubeMatchOutputData, Boolean gotoNextView);

    void prepareFailView(String error);

    void failSaveExit(YoutubePlaylist playlist, CompletePlaylist matchedPlaylist, String error);
}
