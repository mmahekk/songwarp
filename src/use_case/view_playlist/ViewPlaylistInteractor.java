package use_case.view_playlist;
import entity.Playlist;

import java.util.List;

public class ViewPlaylistInteractor implements ViewPlaylistInputBoundary {
    private final ViewPlaylistDataAccessInterface viewPlaylistDataAccess;

    private final ViewPlaylistOutputBoundary viewPlaylistPresenter;

    public ViewPlaylistInteractor(ViewPlaylistDataAccessInterface viewPlaylistDataAccess,
                                  ViewPlaylistOutputBoundary viewPlaylistPresenter) {
        this.viewPlaylistDataAccess = viewPlaylistDataAccess;
        this.viewPlaylistPresenter = viewPlaylistPresenter;

    }

    @Override
    public void execute(ViewPlaylistInputData viewPlaylistInputData) {
        try {
            if (viewPlaylistInputData.getPlaylist() != null) {

                // Get the playlist from the input data
                Playlist playlist = viewPlaylistInputData.getPlaylist();
                // Directly use getPlaylistData method to get the formatted playlist data
                List<String> playlistText = viewPlaylistDataAccess.getPlaylistData(playlist);

                // Create output data object to invoke into presenter success view
                ViewPlaylistOutputData viewPlaylistOutputData = new ViewPlaylistOutputData(playlistText);

                viewPlaylistPresenter.prepareSuccessView(viewPlaylistOutputData);

            } else {
                // Invoke presenter with fail view and error message
                viewPlaylistPresenter.prepareFailView("Please input a valid playlist");
            }
        } catch (Exception e) {viewPlaylistPresenter.prepareFailView("Failed to display playlist");}

    }

}