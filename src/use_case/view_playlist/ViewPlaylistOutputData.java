package use_case.view_playlist;

import java.util.List;

public class ViewPlaylistOutputData {

    private final List<String> playlistText;

    public ViewPlaylistOutputData(List<String> playlistText) {
        this.playlistText = playlistText;
    }

    public List<String> getPlaylistText() {
        return playlistText;
    }
}
