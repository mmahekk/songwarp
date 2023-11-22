package use_case.youtube_put;

import use_case.youtube_match.YoutubeMatchOutputData;

public interface YoutubePutOutputBoundary {
    void prepareSuccessView(YoutubePutOutputData youtubePutOutputData);

    void prepareFailView(String error);
}
