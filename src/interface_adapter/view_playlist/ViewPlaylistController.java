package interface_adapter.view_playlist;

import entity.Playlist;
import use_case.view_playlist.ViewPlaylistInputBoundary;
import use_case.view_playlist.ViewPlaylistInputData;

public class ViewPlaylistController {

    final ViewPlaylistInputBoundary viewPlaylistUseCaseInteractor;

    public ViewPlaylistController(ViewPlaylistInputBoundary viewPlaylistUseCaseInteractor) {

        this.viewPlaylistUseCaseInteractor = viewPlaylistUseCaseInteractor;

    }

    public void execute(Playlist playlist) {

        ViewPlaylistInputData viewPlaylistInputData = new ViewPlaylistInputData(playlist);

        //invoke the use case interactor

        viewPlaylistUseCaseInteractor.execute(viewPlaylistInputData);

    }

}