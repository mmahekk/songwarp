package interface_adapter.save_playlist;

public class SavePlaylistState {
    private String filePath;
    private String error;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}