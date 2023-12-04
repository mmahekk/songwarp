package use_case.save_playlist;

import data_access.TempFileWriterDataAccessObject;

public class SavePlaylistInteractor implements SavePlaylistInputBoundary {
    private final SavePlaylistDataAccessInterface savePlaylistDataAccess;
    private final TempFileWriterDataAccessObject fileWriter;
    private final SavePlaylistOutputBoundary savePlaylistPresenter;

    public SavePlaylistInteractor(SavePlaylistDataAccessInterface savePlaylistDataAccess,
                                  TempFileWriterDataAccessObject fileWriter,
                                  SavePlaylistOutputBoundary savePlaylistPresenter) {
        this.savePlaylistDataAccess = savePlaylistDataAccess;
        this.fileWriter = fileWriter;
        this.savePlaylistPresenter = savePlaylistPresenter;
    }

    @Override
    public void execute(SavePlaylistInputData inputData) {
        try {
            if (inputData.getFilePath() != null) {
                // Create a JSON file from the Playlist object and save it (DAO request)
                savePlaylistDataAccess.createJSONFile(inputData);

                // Get the file path of the saved JSON file
                String filePath = inputData.getFilePath(); // Update this based on your logic

                // Invoke presenter with success view and provide the file path
                SavePlaylistOutputData outputData = new SavePlaylistOutputData(filePath);
                savePlaylistPresenter.prepareSuccessView(outputData);
            } else {
                savePlaylistPresenter.prepareFailView("Please enter a playlist name first");
            }

        } catch (Exception e) {savePlaylistPresenter.prepareFailView("Failed to save playlist: " + e.getMessage());}
    }
}