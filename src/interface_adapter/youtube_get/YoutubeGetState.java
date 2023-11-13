package interface_adapter.youtube_get;

import entity.YoutubePlaylist;

public class YoutubeGetState {
    private YoutubePlaylist playlist;
    private String error;
    public void setError(String error) {
        this.error = error;
    }

    public void setPlaylist(YoutubePlaylist playlist) {
        this.playlist = playlist;
    }

    public YoutubePlaylist getPlaylist() {
        return playlist;
    }

    public String getError() {
        return error;
    }
}
