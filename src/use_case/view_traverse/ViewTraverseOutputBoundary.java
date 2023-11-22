package use_case.view_traverse;

import use_case.save_playlist.SavePlaylistOutputData;

public interface ViewTraverseOutputBoundary {
    void prepareSuccessView();

    void prepareFailView(String error);
}
