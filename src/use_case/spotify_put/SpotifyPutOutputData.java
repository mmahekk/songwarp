package use_case.spotify_put;

import entity.CompletePlaylist;

public class SpotifyPutOutputData {
    private final CompletePlaylist playlist;

    public SpotifyPutOutputData(CompletePlaylist playlist) {
        this.playlist = playlist;
    }

    public CompletePlaylist getPlaylist() {
        return playlist;
    }

    public String getPlaylistYoutubeUrl() {
        String id = this.getPlaylist().getIDs()[0];
        return "https://www.youtube.com/playlist?list=" + id;
    }
}
