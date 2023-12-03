package interface_adapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ProcessPlaylistViewModel extends ViewModel implements ProgressListener {
    public final String TITLE_LABEL = "Successfully retrieved Playlist data.";
    public final String MATCH_BUTTON_LABEL = "Convert";
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

    @Override
    public void onProgressUpdated(int progress) {
        this.getState().setProgress(progress);
        this.setState(this.state);
        this.firePropertyChanged();
    }
}