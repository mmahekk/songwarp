package interface_adapter.load_playlist;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class LoadPlaylistViewModel extends ViewModel {
    public LoadPlaylistViewModel() {
        super("page 1");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
