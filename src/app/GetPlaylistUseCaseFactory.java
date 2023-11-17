package app;

import data_access.TempPlaylistDataAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.load_playlist.LoadPlaylistPresenter;
import interface_adapter.load_playlist.LoadPlaylistViewModel;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import interface_adapter.spotify_get.SpotifyGetViewModel;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import interface_adapter.youtube_get.YoutubeGetViewModel;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;
import use_case.load_playlist.LoadPlaylistInputBoundary;
import use_case.load_playlist.LoadPlaylistInteractor;
import use_case.load_playlist.LoadPlaylistOutputBoundary;
import use_case.spotify_get.SpotifyGetDataAccessInterface;
import use_case.spotify_get.SpotifyGetInputBoundary;
import use_case.spotify_get.SpotifyGetInteractor;
import use_case.spotify_get.SpotifyGetOutputBoundary;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInputBoundary;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import view.InitialView;

import javax.swing.*;
import javax.swing.text.View;
import java.io.IOException;

public class GetPlaylistUseCaseFactory {

    private GetPlaylistUseCaseFactory() {}

    public static InitialView create(ViewManagerModel viewManagerModel,
                                     LoadPlaylistViewModel loadPlaylistViewModel, LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject,
                                     YoutubeGetViewModel youtubeGetViewModel, YoutubeGetDataAccessInterface youtubeGetDataAccessObject,
                                     SpotifyGetViewModel spotifyGetViewModel, SpotifyGetDataAccessInterface spotifyGetDataAccessObject) {
        try {
            LoadPlaylistController loadPlaylistController = createLoadPlaylistUseCase(viewManagerModel, loadPlaylistViewModel, loadPlaylistDataAccessObject);
            YoutubeGetController youtubeGetController = createYoutubeGetUseCase(viewManagerModel, youtubeGetViewModel, youtubeGetDataAccessObject);
            SpotifyGetController spotifyGetController = createSpotifyGetUseCase(viewManagerModel, spotifyGetViewModel, spotifyGetDataAccessObject);
            return new InitialView(); //TODO: add the controllers to the arguments after InitalView is implemented.
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load initial page");
        }
        return null;
    }

    private static LoadPlaylistController createLoadPlaylistUseCase(
            ViewManagerModel viewManagerModel, LoadPlaylistViewModel loadPlaylistViewModel,
            LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject) throws IOException {

        //TODO: change the input arguments when methods are done.
        LoadPlaylistOutputBoundary loadPlaylistOutputBoundary = new LoadPlaylistPresenter();

        TempPlaylistDataAccessObject filewriter = new TempPlaylistDataAccessObject();

        LoadPlaylistInputBoundary loadPlaylistInteractor = new LoadPlaylistInteractor();

        return new LoadPlaylistController();
    }

    private static YoutubeGetController createYoutubeGetUseCase(
            ViewManagerModel viewManagerModel, YoutubeGetViewModel youtubeGetViewModel,
            YoutubeGetDataAccessInterface youtubeGetDataAccessObject) throws IOException {


        YoutubeGetOutputBoundary youtubeGetOutputBoundary = new YoutubeGetPresenter(viewManagerModel, youtubeGetViewModel);

        TempPlaylistDataAccessObject filewriter = new TempPlaylistDataAccessObject();

        YoutubeGetInputBoundary youtubeGetInteractor = new YoutubeGetInteractor(youtubeGetDataAccessObject,
                filewriter, youtubeGetOutputBoundary);

        return new YoutubeGetController(youtubeGetInteractor);
    }

    private static SpotifyGetController createSpotifyGetUseCase (
            ViewManagerModel viewManagerModel, SpotifyGetViewModel spotifyGetViewModel,
            SpotifyGetDataAccessInterface spotifyGetDataAccessObject) throws IOException{

        //TODO: change the input arguments when methods are done.
        SpotifyGetOutputBoundary spotifyGetOutputBoundary = new SpotifyGetPresenter();

        TempPlaylistDataAccessObject filewriter = new TempPlaylistDataAccessObject();

        SpotifyGetInputBoundary spotifyGetInteractor = new SpotifyGetInteractor();

        return new SpotifyGetController();
    }

}
