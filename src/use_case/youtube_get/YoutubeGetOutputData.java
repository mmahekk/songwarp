package use_case.youtube_get;

import entity.YoutubePlaylist;

public class YoutubeGetOutputData {
    private final YoutubePlaylist playlist;

    public YoutubeGetOutputData(YoutubePlaylist playlist) {
        this.playlist = playlist;
    }

    public YoutubePlaylist getPlaylist() {
        return playlist;
    }
}
