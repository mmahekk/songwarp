package use_case.spotify_put;

import entity.CompletePlaylist;

public class SpotifyPutInputData {
    private final CompletePlaylist playlist;
    private final String playlistName;
    private final String userID;

    public SpotifyPutInputData(CompletePlaylist playlist, String playlistName, String userID) {
        this.playlist = playlist;
        this.playlistName = playlistName;
        this.userID = userID;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getUserID() {
        return userID;
    }

    public CompletePlaylist getPlaylist() {
        return playlist;
    }

    public String getSpotifyUrl() {
        String id = this.getPlaylist().getIDs()[1];
        return "https://open.spotify.com/playlist/" + id;
    }
}
