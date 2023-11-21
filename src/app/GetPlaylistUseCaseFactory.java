package app;

import data_access.LoadPlaylistDataAccessObject;
import data_access.SpotifyGetDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeGetDataAccessObject;
import interface_adapter.*;
import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.load_playlist.LoadPlaylistPresenter;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.spotify_get.SpotifyGetPresenter;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
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
import java.io.IOException;

public class GetPlaylistUseCaseFactory {

    private GetPlaylistUseCaseFactory() {}

    public static InitialView create(ViewManagerModel viewManagerModel,
                                     GetPlaylistViewModel getPlaylistViewModel,
                                     ProcessPlaylistViewModel processPlaylistViewModel,
                                     TempFileWriterDataAccessObject fileWriter) {
        try {
            LoadPlaylistController loadPlaylistController = createLoadPlaylistUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, fileWriter);
            YoutubeGetController youtubeGetController = createYoutubeGetUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, fileWriter);
            SpotifyGetController spotifyGetController = createSpotifyGetUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, fileWriter);
            return new InitialView(getPlaylistViewModel, youtubeGetController, spotifyGetController);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load initial page");
        }
        return null;
    }

    private static LoadPlaylistController createLoadPlaylistUseCase(
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, TempFileWriterDataAccessObject fileWriter) throws IOException {

        //TODO: change the input arguments when methods are done.
        LoadPlaylistOutputBoundary loadPlaylistOutputBoundary = new LoadPlaylistPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);
        LoadPlaylistDataAccessInterface loadPlaylistDataAccessObject = new LoadPlaylistDataAccessObject();
        LoadPlaylistInputBoundary loadPlaylistInteractor = new LoadPlaylistInteractor(loadPlaylistDataAccessObject,fileWriter, loadPlaylistOutputBoundary);

        return new LoadPlaylistController(loadPlaylistInteractor);
    }

    private static YoutubeGetController createYoutubeGetUseCase(
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, TempFileWriterDataAccessObject fileWriter) throws IOException {

        YoutubeGetOutputBoundary youtubeGetOutputBoundary = new YoutubeGetPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);

        YoutubeGetDataAccessInterface youtubeGetDataAccessObject = new YoutubeGetDataAccessObject();

        YoutubeGetInputBoundary youtubeGetInteractor = new YoutubeGetInteractor(youtubeGetDataAccessObject,
                fileWriter, youtubeGetOutputBoundary);

        return new YoutubeGetController(youtubeGetInteractor);
    }

    private static SpotifyGetController createSpotifyGetUseCase (
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, TempFileWriterDataAccessObject fileWriter) throws IOException{

        SpotifyGetDataAccessInterface spotifyGetDataAccessObject = new SpotifyGetDataAccessObject();
        SpotifyGetOutputBoundary spotifyGetOutputBoundary = new SpotifyGetPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);

        SpotifyGetInputBoundary spotifyGetInteractor = new SpotifyGetInteractor(spotifyGetDataAccessObject, fileWriter, spotifyGetOutputBoundary);

        return new SpotifyGetController(spotifyGetInteractor);
    }
}
