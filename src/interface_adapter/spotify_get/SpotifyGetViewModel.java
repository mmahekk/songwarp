package interface_adapter.spotify_get;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class SpotifyGetViewModel extends ViewModel {

    private SpotifyGetState state = new SpotifyGetState();
    public SpotifyGetViewModel() {
        super("page 1");
    }


    public SpotifyGetState getState() {
        return state;
    }

    public void setState(SpotifyGetState state){
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
