package interface_adapter.spotify_get;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class SpotifyGetViewModel extends ViewModel {
    public SpotifyGetViewModel() {
        super("page 1");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
