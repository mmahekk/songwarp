package use_case.save_playlist;

import entity.CompletePlaylist;
import entity.Playlist;

public class SavePlaylistInputData {
    private final Playlist playlist;
    private final String filePath;

    private final Playlist incompletePlaylist;

    public SavePlaylistInputData(Playlist playlist, String filePath, Playlist incompletePlaylist) {
        this.playlist = playlist;
        this.filePath = filePath;
        this.incompletePlaylist = incompletePlaylist;
    }

    public Playlist getIncompletePlaylist() {return incompletePlaylist;}

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getFilePath() {
        return filePath;
    }
}