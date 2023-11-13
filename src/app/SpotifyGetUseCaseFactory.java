package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import interface_adapter.spotify_get.SpotifyGetViewModel;
import use_case.spotify_get.SpotifyGetDataAccessInterface;
import use_case.spotify_get.SpotifyGetInputBoundary;
import use_case.spotify_get.SpotifyGetInteractor;
import use_case.spotify_get.SpotifyGetOutputBoundary;
import view.InitialView;
import data_access.TempPlaylistDataAccessObject;

import javax.swing.*;
import java.io.IOException;


public class SpotifyGetUseCaseFactory {

    private SpotifyGetUseCaseFactory() {}

    public static InitialView create(ViewManagerModel ViewManagerModel, SpotifyGetViewModel SpotifyGetViewModel,
                                     SpotifyGetDataAccessInterface SpotifyGetDataAccessObject) {

        try {
            SpotifyGetController SpotifyGetController = createSpotifyGetUseCase(ViewManagerModel, SpotifyGetViewModel, SpotifyGetDataAccessObject);
            return new InitialView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not get Spotify playlist");
        }
        return null;
    }

    private static SpotifyGetController createSpotifyGetUseCase(
            ViewManagerModel ViewManagerModel, SpotifyGetViewModel SpotifyGetViewModel,
            SpotifyGetDataAccessInterface SpotifyGetDataAccessObject) throws IOException {

        // TODO: right now the spotify use case classes aren't properly implemented so they do not take in arguments,
        // TODO: so I commented out the input arguments, but needs to be uncommented once spotify methods are implemented.
        SpotifyGetOutputBoundary SpotifyGetOutputBoundary = new SpotifyGetPresenter(//ViewManagerModel, SpotifyGetViewModel
                );

        TempPlaylistDataAccessObject fileWriter = new TempPlaylistDataAccessObject();

        SpotifyGetInputBoundary SpotifyGetInteractor = new SpotifyGetInteractor(//SpotifyGetDataAccessObject,
                //fileWriter, SpotifyGetOutputBoundary
                );

        return new SpotifyGetController(//SpotifyGetInteractor
                );
    }
}
