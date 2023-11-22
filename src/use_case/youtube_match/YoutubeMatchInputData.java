package use_case.youtube_match;

import entity.Playlist;
import entity.YoutubePlaylist;

public class YoutubeMatchInputData {
    private final YoutubePlaylist playlist;
    private final Boolean gotoNextView;
    private final Playlist incompletePlaylist;
    private final int songLimit;

    public YoutubeMatchInputData(YoutubePlaylist playlist, Playlist incompletePlaylist, Boolean gotoNextView, int songLimit) {
        this.playlist = playlist;
        this.incompletePlaylist = incompletePlaylist;
        this.gotoNextView = gotoNextView;
        this.songLimit = songLimit;
    }

    public YoutubePlaylist getPlaylist() {return playlist;}

    public Playlist getIncompletePlaylist() {return incompletePlaylist;}

    public Boolean getGotoNextView() {return gotoNextView;}

    public int getSongLimit() {return songLimit;}
}
