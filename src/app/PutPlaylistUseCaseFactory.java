package app;

import data_access.SavePlaylistDataAccessObject;
import data_access.SpotifyPutDataAccessObject;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubePutDataAccessObject;
import interface_adapter.*;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.save_playlist.SavePlaylistPresenter;
import interface_adapter.save_playlist.SavePlaylistViewModel;
import interface_adapter.spotify_put.SpotifyPutController;
import interface_adapter.spotify_put.SpotifyPutPresenter;
import interface_adapter.view_traverse.ViewTraverseController;
import interface_adapter.view_traverse.ViewTraversePresenter;
import interface_adapter.youtube_put.YoutubePutController;
import interface_adapter.youtube_put.YoutubePutPresenter;
import use_case.save_playlist.SavePlaylistDataAccessInterface;
import use_case.save_playlist.SavePlaylistInputBoundary;
import use_case.save_playlist.SavePlaylistInteractor;
import use_case.save_playlist.SavePlaylistOutputBoundary;
import use_case.spotify_put.SpotifyPutDataAccessInterface;
import use_case.spotify_put.SpotifyPutInputBoundary;
import use_case.spotify_put.SpotifyPutInteractor;
import use_case.spotify_put.SpotifyPutOutputBoundary;
import use_case.view_traverse.ViewTraverseInteractor;
import use_case.view_traverse.ViewTraverseOutputBoundary;
import use_case.view_traverse.ViewTraverseInputBoundary;
import use_case.youtube_put.YoutubePutDataAccessInterface;
import use_case.youtube_put.YoutubePutInputBoundary;
import use_case.youtube_put.YoutubePutInteractor;
import use_case.youtube_put.YoutubePutOutputBoundary;
import view.OutputPageView;

import javax.swing.*;
import java.io.IOException;

public class PutPlaylistUseCaseFactory {

    private PutPlaylistUseCaseFactory() {}

    public static OutputPageView create(ViewManagerModel viewManagerModel,
                                        PutPlaylistViewModel putPlaylistViewModel,
                                        ProcessPlaylistViewModel processPlaylistViewModel,
                                        GetPlaylistViewModel getPlaylistViewModel,
                                        TempFileWriterDataAccessObject fileWriter) {
        try {
            SavePlaylistController savePlaylistController = createSavePlaylistUseCase(viewManagerModel, putPlaylistViewModel, fileWriter);
            ViewTraverseController viewTraverseController = createViewTraverseUseCase(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
            YoutubePutController youtubePutController = createYoutubePutUseCase(viewManagerModel, putPlaylistViewModel);
            SpotifyPutController spotifyPutController = createSpotifyPutUseCase(viewManagerModel, putPlaylistViewModel);
            return new OutputPageView(putPlaylistViewModel, getPlaylistViewModel, savePlaylistController, viewTraverseController, youtubePutController, spotifyPutController);
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

    private static ViewTraverseController createViewTraverseUseCase (
            ViewManagerModel viewManagerModel, GetPlaylistViewModel getPlaylistViewModel,
            ProcessPlaylistViewModel processPlaylistViewModel, PutPlaylistViewModel putPlaylistViewModel) throws IOException{

        ViewTraverseOutputBoundary viewTraverseOutputBoundary = new ViewTraversePresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel);
        ViewTraverseInputBoundary viewTraverseInteractor = new ViewTraverseInteractor(viewTraverseOutputBoundary);

        return new ViewTraverseController(viewTraverseInteractor);
    }

    public static YoutubePutController createYoutubePutUseCase(ViewManagerModel viewManagerModel, PutPlaylistViewModel putPlaylistViewModel) {

        YoutubePutDataAccessInterface youtubePutDataAccessObject = new YoutubePutDataAccessObject();
        YoutubePutOutputBoundary youtubePutPresenter = new YoutubePutPresenter(viewManagerModel, putPlaylistViewModel);

        YoutubePutInputBoundary youtubePutInteractor = new YoutubePutInteractor(youtubePutDataAccessObject, youtubePutPresenter);

        return new YoutubePutController(youtubePutInteractor);
    }

    public static SpotifyPutController createSpotifyPutUseCase(ViewManagerModel viewManagerModel, PutPlaylistViewModel putPlaylistViewModel) {

        SpotifyPutDataAccessInterface spotifyPutDataAccessObject = new SpotifyPutDataAccessObject();
        SpotifyPutOutputBoundary spotifyPutPresenter = new SpotifyPutPresenter(viewManagerModel, putPlaylistViewModel);

        SpotifyPutInputBoundary spotifyPutInteractor = new SpotifyPutInteractor(spotifyPutDataAccessObject, spotifyPutPresenter);

        return new SpotifyPutController(spotifyPutInteractor);
    }
}

