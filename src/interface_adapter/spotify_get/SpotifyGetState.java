package interface_adapter.spotify_get;

import entity.SpotifyPlaylist;

public class SpotifyGetState {

    private SpotifyPlaylist playlist;
    public void setPlaylistGetError(String error) {
    }

    public void setPlaylist(SpotifyPlaylist playlist) {
        this.playlist = playlist;
    }

    public SpotifyPlaylist getPlaylist() {
        return playlist;
    }
}
