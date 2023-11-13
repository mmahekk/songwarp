package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import interface_adapter.youtube_get.YoutubeGetViewModel;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInputBoundary;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import view.InitialView;
import data_access.TempPlaylistDataAccessObject;

import javax.swing.*;
import java.io.IOException;


public class YoutubeGetUseCaseFactory {

    private YoutubeGetUseCaseFactory() {}

    public static InitialView create(ViewManagerModel ViewManagerModel, YoutubeGetViewModel YoutubeGetViewModel,
                                     YoutubeGetDataAccessInterface YoutubeGetDataAccessObject) {

        try {
            YoutubeGetController youtubeGetController = createYoutubeGetUseCase(ViewManagerModel, YoutubeGetViewModel, YoutubeGetDataAccessObject);
            return new InitialView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not get youtube playlist");
        }
        return null;
    }

    private static YoutubeGetController createYoutubeGetUseCase(
            ViewManagerModel ViewManagerModel, YoutubeGetViewModel YoutubeGetViewModel,
            YoutubeGetDataAccessInterface YoutubeGetDataAccessObject) throws IOException {

        YoutubeGetOutputBoundary youtubeGetOutputBoundary = new YoutubeGetPresenter(ViewManagerModel, YoutubeGetViewModel);

        TempPlaylistDataAccessObject fileWriter = new TempPlaylistDataAccessObject();

        YoutubeGetInputBoundary youtubeGetInteractor = new YoutubeGetInteractor(YoutubeGetDataAccessObject,
                fileWriter, youtubeGetOutputBoundary);

        return new YoutubeGetController(youtubeGetInteractor);
    }
}
