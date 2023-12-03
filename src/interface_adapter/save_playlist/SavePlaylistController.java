package interface_adapter.save_playlist;

import entity.Playlist;
import use_case.save_playlist.SavePlaylistInputBoundary;
import use_case.save_playlist.SavePlaylistInputData;


public class SavePlaylistController {
    private final SavePlaylistInputBoundary savePlaylistUseCaseInteractor;

    public SavePlaylistController(SavePlaylistInputBoundary savePlaylistUseCaseInteractor) {
        this.savePlaylistUseCaseInteractor = savePlaylistUseCaseInteractor;
    }

    public void execute(String filePath, Playlist playlist, Playlist incompletePlaylist) {
        try {
            // Create input data for the use case
            SavePlaylistInputData inputData = new SavePlaylistInputData(playlist, filePath, incompletePlaylist);

            // Invoke the use case interactor
            savePlaylistUseCaseInteractor.execute(inputData);
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }
    }

}