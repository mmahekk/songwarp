package use_case.load_playlist;

import data_access.LoadPlaylistDataAccessObject;

public interface LoadPlaylistOutputBoundary {
    void prepareSuccessView(LoadPlaylistOutputData outputData);

    void prepareFailView(String error);
}
