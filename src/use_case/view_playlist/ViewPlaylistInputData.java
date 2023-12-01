package use_case.view_playlist;

import entity.Playlist;

public class ViewPlaylistInputData {
    private final Playlist playlist;

    public ViewPlaylistInputData(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}
