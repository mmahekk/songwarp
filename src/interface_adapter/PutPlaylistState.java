package interface_adapter;

import entity.CompletePlaylist;
import entity.Playlist;
import org.json.JSONObject;

public class PutPlaylistState {
    private Playlist playlist;
    private String error;
    private JSONObject saveFileText;
    private String playlistName;
    private String successUrl;

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

}
