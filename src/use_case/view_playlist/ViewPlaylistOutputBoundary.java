package use_case.view_playlist;

public interface ViewPlaylistOutputBoundary {

    void prepareSuccessView(ViewPlaylistOutputData viewPlaylistOutputData);

    void prepareFailView(String error);
}