package interface_adapter;

import entity.Playlist;
import entity.YoutubePlaylist;

public class GetPlaylistState {
    private Playlist playlist;
    private String error;
    private String url;
    public void setError(String error) {
        this.error = error;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getError() {
        return error;
    }

    public String getUrlInput() {
        return url;
    }

    public void setUrlInput(String url) {
        this.url = url;
    }
}