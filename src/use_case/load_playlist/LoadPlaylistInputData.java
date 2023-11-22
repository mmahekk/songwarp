package use_case.load_playlist;

public class LoadPlaylistInputData {
    private final String filePath;

    public LoadPlaylistInputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
