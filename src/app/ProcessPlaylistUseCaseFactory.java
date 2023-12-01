package app;

import data_access.*;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.view_traverse.ViewTraverseController;
import interface_adapter.youtube_match.YoutubeMatchController;
import interface_adapter.youtube_match.YoutubeMatchPresenter;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchInputBoundary;
import use_case.youtube_match.YoutubeMatchInteractor;
import use_case.youtube_match.YoutubeMatchOutputBoundary;
import view.MatchOrSplitSelectionView;

import javax.swing.*;
import java.io.IOException;

public class ProcessPlaylistUseCaseFactory {

    private ProcessPlaylistUseCaseFactory() {}

    public static MatchOrSplitSelectionView create(ViewManagerModel viewManagerModel,
                                                   ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel,
                                                   TempFileWriterDataAccessObject fileWriter, TempFileWriterDataAccessObject backupFileWriter,
                                                   SavePlaylistController savePlaylistController, ViewTraverseController viewTraverseController) {
        try {
            YoutubeMatchController youtubeMatchController = createYoutubeMatchUseCase(viewManagerModel, processPlaylistViewModel, putPlaylistViewModel, fileWriter, backupFileWriter);
            return new MatchOrSplitSelectionView(processPlaylistViewModel, youtubeMatchController, savePlaylistController, viewTraverseController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load initial page");
        }
        return null;
    }

    private static YoutubeMatchController createYoutubeMatchUseCase(
            ViewManagerModel viewManagerModel, ProcessPlaylistViewModel processPlaylistViewModel,
            PutPlaylistViewModel putPlaylistViewModel, TempFileWriterDataAccessObject fileWriter,
            TempFileWriterDataAccessObject backupFileWriter) throws IOException {

        YoutubeMatchOutputBoundary presenter = new YoutubeMatchPresenter(viewManagerModel, processPlaylistViewModel, putPlaylistViewModel);

        YoutubeMatchDataAccessInterface dataAccessObject = new YoutubeMatchDataAccessObject();

        YoutubeMatchInputBoundary interactor = new YoutubeMatchInteractor(dataAccessObject, fileWriter, backupFileWriter, presenter);

        return new YoutubeMatchController(interactor);
    }
}
