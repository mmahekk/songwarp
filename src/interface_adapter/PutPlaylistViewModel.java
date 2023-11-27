package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PutPlaylistViewModel extends ViewModel {
    public final String TITLE_LABEL = "Output page.";
    public final String VIEW_OUTPUT_LABEL = "Playlist JSON";
    public final String YOUTUBE_PUT_BUTTON_LABEL = "Upload playlist(s) to youtube";
    public final String SPOTIFY_PUT_BUTTON_LABEL = "Upload playlist(s) to spotify";
    public final String SAVE_PLAYLIST_BUTTON_LABEL = "Save";
    public final String SAVE_PLAYLIST_TITLE = "Enter the playlist name";
    public final String VIEW_PLAYLIST_BUTTON_LABEL = "view playlist as a JSON in textbox";
    public final String SAVE_CHANGES_BUTTON_LABEL = "Save your json edits (risky)";
    public final String RESTART_BUTTON_LABEL = "Restart program";

    private PutPlaylistState state = new PutPlaylistState();
    public PutPlaylistViewModel() {
        super("page 3");
    }

    public PutPlaylistState getState() {
        return state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void setState(PutPlaylistState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
