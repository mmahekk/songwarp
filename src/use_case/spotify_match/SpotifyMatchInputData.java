package use_case.spotify_match;

import entity.Playlist;
import entity.SpotifyPlaylist;



public class SpotifyMatchInputData {
    private final SpotifyPlaylist playlist;
    private final Boolean gotoNextView;
    private final Playlist incompletePlaylist;
    private final int songLimit;

    public SpotifyMatchInputData(SpotifyPlaylist playlist, Playlist incompletePlaylist, Boolean gotoNextView, int songLimit) {
        this.playlist = playlist;
        this.incompletePlaylist = incompletePlaylist;
        this.gotoNextView = gotoNextView;
        this.songLimit = songLimit;
    }

    public SpotifyPlaylist getPlaylist() {return playlist;}

    public Playlist getIncompletePlaylist() {return incompletePlaylist;}

    public Boolean getGotoNextView() {return gotoNextView;}

    public int getSongLimit() {return songLimit;}
}
