package use_case.save_playlist;

import entity.Playlist;

public class SavePlaylistInputData {
    private final Playlist playlist;
    private final String filePath;

    public SavePlaylistInputData(Playlist playlist, String filePath) {
        this.playlist = playlist;
        this.filePath = filePath;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public String getFilePath() {
        return filePath;
    }
}