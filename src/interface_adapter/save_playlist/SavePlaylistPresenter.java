package interface_adapter.save_playlist;

import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.save_playlist.SavePlaylistOutputData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SavePlaylistPresenter implements SavePlaylistOutputBoundary {
    private final SavePlaylistViewModel savePlaylistViewModel;
    private final PutPlaylistViewModel putPlaylistViewModel;
    private final ViewManagerModel viewManagerModel;

    public SavePlaylistPresenter(ViewManagerModel viewManagerModel, SavePlaylistViewModel savePlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) {
        this.savePlaylistViewModel = savePlaylistViewModel;
        this.putPlaylistViewModel = putPlaylistViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SavePlaylistOutputData outputData) {
        SavePlaylistState currentState = savePlaylistViewModel.getState();
        currentState.setFilePath(outputData.getFilePath());
        currentState.setError(null);

        savePlaylistViewModel.setState(currentState);
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SavePlaylistState savePlaylistState = savePlaylistViewModel.getState();
        savePlaylistState.setFilePath(null);
        savePlaylistState.setError(error);
        putPlaylistViewModel.getState().setError(error);

        System.out.println(savePlaylistState.getError());
        // savePlaylistViewModel.setState(currentState);
        putPlaylistViewModel.firePropertyChanged();
    }
}