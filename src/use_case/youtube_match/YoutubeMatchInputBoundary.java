package use_case.youtube_match;

import use_case.youtube_get.YoutubeGetInputData;

import java.io.IOException;

public interface YoutubeMatchInputBoundary {
    void execute(YoutubeMatchInputData youtubeMatchInputData);
}
