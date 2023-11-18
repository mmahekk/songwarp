package use_case.save_playlist;

public interface SavePlaylistInputBoundary {
    // Execute function that takes in the input data and calls the interactor
    void execute(SavePlaylistInputData inputData);
}