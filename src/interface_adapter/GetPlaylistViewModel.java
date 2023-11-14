package interface_adapter;

import interface_adapter.youtube_get.YoutubeGetState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GetPlaylistViewModel extends ViewModel {
    public final String TITLE_LABEL = "Input playlist view";
    public final String URL_LABEL = "Enter playlist url";
    public final String YOUTUBEGET_BUTTON_LABEL = "From Youtube Url";
    public final String SPOTIFYGET_BUTTON_LABEL = "From Spotify Url";
    public final String LOADPLAYLIST_BUTTON_LABEL = "Load a save file";

    private GetPlaylistState state = new GetPlaylistState();
    public GetPlaylistViewModel() {
        super("page 1");
    }

    public GetPlaylistState getState() {
        return state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void setState(GetPlaylistState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}