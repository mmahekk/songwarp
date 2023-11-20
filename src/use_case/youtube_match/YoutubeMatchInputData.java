package use_case.youtube_match;

import entity.YoutubePlaylist;

public class YoutubeMatchInputData {
    private YoutubePlaylist playlist;

    public YoutubeMatchInputData(YoutubePlaylist playlist) {
        this.playlist = playlist;
    }

    public YoutubePlaylist getPlaylist() {return playlist;}
}
