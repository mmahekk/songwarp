package interface_adapter.view_playlist;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class ViewPlaylistViewModel extends ViewModel {
    public ViewPlaylistViewModel() {
        super("page 3");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
