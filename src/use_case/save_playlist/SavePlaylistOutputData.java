package use_case.save_playlist;

public class SavePlaylistOutputData {
    private final String filePath;

    public SavePlaylistOutputData(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}