package interface_adapter.youtube_get;

import entity.YoutubePlaylist;

public class YoutubeGetState {
    private YoutubePlaylist playlist;
    public void setPlaylistGetError(String error) {
    }

    public void setPlaylist(YoutubePlaylist playlist) {
        this.playlist = playlist;
    }

    public YoutubePlaylist getPlaylist() {
        return playlist;
    }
}
