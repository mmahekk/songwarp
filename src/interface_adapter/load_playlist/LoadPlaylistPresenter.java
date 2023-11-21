package interface_adapter.load_playlist;

import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import use_case.load_playlist.LoadPlaylistOutputData;

import javax.swing.text.View;
import java.beans.PropertyChangeSupport;

public class LoadPlaylistPresenter implements LoadPlaylistOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final GetPlaylistViewModel loadPlaylistViewModel;
    private final ProcessPlaylistViewModel processPlaylistViewModel;

    public LoadPlaylistPresenter(ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
                                 ProcessPlaylistViewModel processPlaylistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loadPlaylistViewModel = getPlaylistViewModel;
        this.processPlaylistViewModel = processPlaylistViewModel;
    }
    @Override
    public void prepareSuccessView(LoadPlaylistOutputData outputData) {
        //TODO
    }

    @Override
    public void prepareFailView(String error) {
        //TODO
    }
}
