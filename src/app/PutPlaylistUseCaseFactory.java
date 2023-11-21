package app;

import data_access.SavePlaylistDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import interface_adapter.*;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.save_playlist.SavePlaylistPresenter;
import interface_adapter.save_playlist.SavePlaylistViewModel;
import use_case.save_playlist.SavePlaylistDataAccessInterface;
import use_case.save_playlist.SavePlaylistInputBoundary;
import use_case.save_playlist.SavePlaylistInteractor;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import view.OutputPageView;

import javax.swing.*;
import java.io.IOException;

public class PutPlaylistUseCaseFactory {

    private PutPlaylistUseCaseFactory() {}

    public static OutputPageView create(ViewManagerModel viewManagerModel,
                                        PutPlaylistViewModel putPlaylistViewModel,
                                        GetPlaylistViewModel getPlaylistViewModel,
                                        TempFileWriterDataAccessObject fileWriter) {
        try {
            SavePlaylistController savePlaylistController = createSavePlaylistUseCase(viewManagerModel, putPlaylistViewModel, fileWriter);
            return new OutputPageView(putPlaylistViewModel, getPlaylistViewModel, savePlaylistController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load initial page");
        }
        return null;
    }

    private static SavePlaylistController createSavePlaylistUseCase (
            ViewManagerModel viewManagerModel, PutPlaylistViewModel putPlaylistViewModel, TempFileWriterDataAccessObject fileWriter) throws IOException{

        SavePlaylistDataAccessInterface savePlaylistDataAccessObject = new SavePlaylistDataAccessObject();
        SavePlaylistOutputBoundary savePlaylistOutputBoundary = new SavePlaylistPresenter(viewManagerModel, new SavePlaylistViewModel(), putPlaylistViewModel);

        SavePlaylistInputBoundary savePlaylistInteractor = new SavePlaylistInteractor(savePlaylistDataAccessObject, fileWriter, savePlaylistOutputBoundary);

        return new SavePlaylistController(savePlaylistInteractor);
    }
}

