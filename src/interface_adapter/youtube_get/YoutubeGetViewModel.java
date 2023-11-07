package interface_adapter.youtube_get;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class YoutubeGetViewModel extends ViewModel {
    private YoutubeGetState state = new YoutubeGetState();
    public YoutubeGetViewModel() {
        super("page 1");
    }

    public YoutubeGetState getState() {
        return state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void setState(YoutubeGetState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
