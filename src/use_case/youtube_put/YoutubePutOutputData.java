package use_case.youtube_put;

import entity.CompletePlaylist;

public class YoutubePutOutputData {
    private final CompletePlaylist playlist;

    public YoutubePutOutputData(CompletePlaylist playlist) {
        this.playlist = playlist;
    }

    public CompletePlaylist getPlaylist() {
        return playlist;
    }

    public String getPlaylistSpotifyUrl() {
        String id = this.getPlaylist().getIDs()[1];
        return "https://open.spotify.com/playlist/" + id;
    }
}
