package use_case.youtube_match;

import use_case.youtube_get.YoutubeGetOutputData;

public interface YoutubeMatchOutputBoundary {
    void prepareSuccessView(YoutubeMatchOutputData youtubeMatchOutputData);

    void prepareFailView(String error);
}
