package interface_adapter.save_playlist;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class SavePlaylistViewModel extends ViewModel {

    private SavePlaylistState state = new SavePlaylistState();
    public SavePlaylistViewModel() {
        super("page 1");
    }


    public SavePlaylistState getState() {
        return state;
    }

    public void setState(SavePlaylistState state){
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
