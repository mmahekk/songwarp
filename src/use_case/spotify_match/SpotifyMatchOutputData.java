package use_case.spotify_match;

import entity.CompletePlaylist;

public class SpotifyMatchOutputData {

    private final CompletePlaylist playlist;

    public SpotifyMatchOutputData(CompletePlaylist playlist) {
        this.playlist = playlist;
    }

    public CompletePlaylist getPlaylist() {
        return playlist;
    }
}
