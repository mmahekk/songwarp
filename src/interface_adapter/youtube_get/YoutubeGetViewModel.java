package interface_adapter.youtube_get;

import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class YoutubeGetViewModel extends GetPlaylistViewModel {
    private final YoutubeGetState state = new YoutubeGetState();
    public YoutubeGetState getState() {
        return state;
    }
}
