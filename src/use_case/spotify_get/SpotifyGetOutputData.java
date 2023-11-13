package use_case.spotify_get;

import entity.SpotifyPlaylist;

public class SpotifyGetOutputData {

    private final SpotifyPlaylist playlist;
    private boolean useCaseFailed;

    public SpotifyGetOutputData(SpotifyPlaylist playlist, boolean useCaseFailed) {
        this.playlist = playlist;
        this.useCaseFailed = useCaseFailed;
    }

    public SpotifyPlaylist getPlaylist() {
        return playlist;
    }




}
