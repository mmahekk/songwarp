package use_case.load_playlist;

import entity.Playlist;

public class LoadPlaylistOutputData {
    private final Playlist playlist;

    public LoadPlaylistOutputData(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return this.playlist;
    }
}
