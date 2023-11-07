package use_case.youtube_get;

public interface YoutubeGetOutputBoundary {
    void prepareSuccessView(YoutubeGetOutputData youtubeGetOutputData);

    void prepareFailView(String error);
}
