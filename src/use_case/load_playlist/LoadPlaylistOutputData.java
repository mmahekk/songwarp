package use_case.load_playlist;

import entity.CompletePlaylist;
import entity.Playlist;

public class LoadPlaylistOutputData {
    private Playlist playlist;
    private CompletePlaylist incompletePlaylist;

    public void setPlaylist(Playlist playlist) {this.playlist = playlist;}

    public Playlist getPlaylist() {
        return this.playlist;
    }

    public void setCompletePlaylist(CompletePlaylist incompletePlaylist) {this.incompletePlaylist = incompletePlaylist;}

    public CompletePlaylist getCompletePlaylist() {return this.incompletePlaylist;}
}
