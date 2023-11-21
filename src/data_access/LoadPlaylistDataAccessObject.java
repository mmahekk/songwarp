package data_access;

import entity.Playlist;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;

public class LoadPlaylistDataAccessObject implements LoadPlaylistDataAccessInterface {
    private final String file;

    public LoadPlaylistDataAccessObject(){
        this.file = "temp.json";
    }

    public Playlist LoadPlaylist() {
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject(file);
        return fileWriter.readPlaylistJSON();
    }
}
