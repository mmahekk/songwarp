package interface_adapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ProcessPlaylistViewModel extends ViewModel {
    public final String TITLE_LABEL = "Successfully retrieved Playlist data. Now pick an option:";
    public final String MATCH_BUTTON_LABEL = "Convert playlist";
    public final String GENRESPLIT_BUTTON_LABEL = "Split playlist by genre";

    private ProcessPlaylistState state = new ProcessPlaylistState();
    public ProcessPlaylistViewModel() {
        super("page 2");
    }

    public ProcessPlaylistState getState() {
        return state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void setState(ProcessPlaylistState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}