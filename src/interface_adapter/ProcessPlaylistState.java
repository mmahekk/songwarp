package interface_adapter;

import entity.CompletePlaylist;
import entity.Playlist;

public class ProcessPlaylistState {
    private Playlist playlist;
    private CompletePlaylist incompletePlaylist;
    private String error;
    private String url;
    private Boolean forcedToSave;

    public void setError(String error) {
        this.error = error;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setIncompletePlaylist(CompletePlaylist playlist) {
        this.incompletePlaylist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public CompletePlaylist getIncompletePlaylist() {
        return incompletePlaylist;
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

    public void setForcedToSave(Boolean b) {
        this.forcedToSave = b;
    }

    public Boolean getForcedToSave() {return forcedToSave;}
}