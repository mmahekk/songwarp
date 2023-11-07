package interface_adapter.spotify_match;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class SpotifyMatchViewModel extends ViewModel {
    public SpotifyMatchViewModel() {
        super("page 2");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
