package use_case.youtube_match;

import entity.CompletePlaylist;

public class YoutubeMatchOutputData {
    private final CompletePlaylist playlist;

    public YoutubeMatchOutputData(CompletePlaylist playlist) {
        this.playlist = playlist;
    }

    public CompletePlaylist getPlaylist() {
        return playlist;
    }
}
