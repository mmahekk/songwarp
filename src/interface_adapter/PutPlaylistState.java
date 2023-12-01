package interface_adapter;

import entity.CompletePlaylist;
import entity.Playlist;
import org.json.JSONObject;

public class PutPlaylistState {
    private Playlist playlist;
    private CompletePlaylist incompletePlaylist;
    private String error;
    private JSONObject saveFileText;
    private String playlistName;
    private String successUrl;
    private String outputTextView;

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

    public CompletePlaylist getIncompletePlaylist() {return incompletePlaylist;}

    public void setIncompletePlaylist(CompletePlaylist completePlaylist) {this.incompletePlaylist = completePlaylist;}
    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    public void setSuccessUrl(String url) {
        this.successUrl = url;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSaveFileText(JSONObject jsonObject) {
        this.saveFileText = jsonObject;
    }

    public JSONObject getSaveFileText() {
        return saveFileText;
    }

    // the following was added for viewplaylist use case
    public void setOutputTextView(String outputTextView) {
        this.outputTextView = outputTextView;
    }

    public String getOutputTextView() {
        return outputTextView;
    }

}
