package interface_adapter.save_playlist;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class SavePlaylistViewModel extends ViewModel {

    private SavePlaylistState state = new SavePlaylistState();
    public SavePlaylistViewModel() {
        super("page 3");
    }

    public SavePlaylistState getState() {
        return state;
    }

    public void setState(SavePlaylistState state){
        this.state = state;
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
