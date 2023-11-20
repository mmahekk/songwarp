package interface_adapter.save_playlist;

import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.save_playlist.SavePlaylistOutputData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SavePlaylistPresenter implements SavePlaylistOutputBoundary {
    private final SavePlaylistViewModel savePlaylistViewModel;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public SavePlaylistPresenter(SavePlaylistViewModel savePlaylistViewModel) {
        this.savePlaylistViewModel = savePlaylistViewModel;
    }

    @Override
    public void prepareSuccessView(SavePlaylistOutputData outputData) {
        SavePlaylistState currentState = savePlaylistViewModel.getState();
        currentState.setFilePath(outputData.getFilePath());
        currentState.setError(null);

        savePlaylistViewModel.setState(currentState);
        support.firePropertyChange("state", null, currentState);
    }

    @Override
    public void prepareFailView(String error) {
        SavePlaylistState currentState = savePlaylistViewModel.getState();
        currentState.setFilePath(null);
        currentState.setError(error);

        savePlaylistViewModel.setState(currentState);
        support.firePropertyChange("state", null, currentState);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}