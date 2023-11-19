package use_case.save_playlist;

public interface SavePlaylistOutputBoundary {
    void prepareSuccessView(SavePlaylistOutputData outputData);

    void prepareFailView(String error);
}