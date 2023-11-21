package interface_adapter.save_playlist;

import entity.Playlist;
import use_case.save_playlist.SavePlaylistInputBoundary;
import use_case.save_playlist.SavePlaylistInputData;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.save_playlist.SavePlaylistOutputData;

public class SavePlaylistController {
    private final SavePlaylistInputBoundary savePlaylistUseCaseInteractor;

    public SavePlaylistController(SavePlaylistInputBoundary savePlaylistUseCaseInteractor) {
        this.savePlaylistUseCaseInteractor = savePlaylistUseCaseInteractor;
    }

    public void execute(String filePath, Playlist playlist) {
        try {
            // Create input data for the use case
            SavePlaylistInputData inputData = new SavePlaylistInputData(playlist, filePath);

            // Invoke the use case interactor
            savePlaylistUseCaseInteractor.execute(inputData);
        } catch (Exception e) {
            // Handle exceptions as needed
            e.printStackTrace();
        }
    }

//    // This method is typically called by the presenter to handle the output
//    public void handleSavePlaylistOutput(SavePlaylistOutputData outputData) {
//        // Delegate the output handling to the presenter
//        savePlaylistPresenter.prepareSuccessView(outputData);
//    }
//
//    // This method is typically called by the presenter to handle errors
//    public void handleSavePlaylistError(String error) {
//        // Delegate the error handling to the presenter
//        savePlaylistPresenter.prepareFailView(error);
//    }
}