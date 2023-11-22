package use_case.youtube_put;

import entity.CompletePlaylist;

public class YoutubePutInputData {
    private final CompletePlaylist playlist;
    private final String playlistName;
    private final String userID;

    public YoutubePutInputData(CompletePlaylist playlist, String playlistName, String userID) {
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

    public String getYoutubeUrl() {
        String id = this.getPlaylist().getIDs()[0];
        return "https://www.youtube.com/playlist?list=" + id;
    }
}
