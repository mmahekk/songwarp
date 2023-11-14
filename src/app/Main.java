package app;

import data_access.TempPlaylistDataAccessObject;
import data_access.YoutubeGetDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.load_playlist.LoadPlaylistViewModel;
import interface_adapter.spotify_get.SpotifyGetViewModel;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import interface_adapter.youtube_get.YoutubeGetViewModel;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import view.InitialView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Program Test");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        YoutubeGetViewModel youtubeGetViewModel = new YoutubeGetViewModel();
//        SpotifyGetViewModel spotifyGetViewModel = new SpotifyGetViewModel();
//        LoadPlaylistViewModel loadPlaylistViewModel = new LoadPlaylistViewModel();
        GetPlaylistViewModel getPlaylistViewModel = new GetPlaylistViewModel();

        TempPlaylistDataAccessObject fileWriter = new TempPlaylistDataAccessObject("temp.json");

        //TODO: note, the following code is temporary and should be replaced once usecase factories are made
        YoutubeGetDataAccessInterface dataAccessObject = new YoutubeGetDataAccessObject();
        YoutubeGetOutputBoundary outputBoundary = new YoutubeGetPresenter(viewManagerModel, getPlaylistViewModel);
        YoutubeGetInteractor youtubeGetInteractor = new YoutubeGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        YoutubeGetController youtubeGetController = new YoutubeGetController(youtubeGetInteractor);
        InitialView initialView = new InitialView(getPlaylistViewModel, youtubeGetController);
//          InitialView initialView = GetPlaylistUseCaseFactory.create(
//          viewManagerModel, getPlaylistViewModel, youtubeGetViewModel, spotifyGetViewModel, loadPlaylistViewModel, tempPlaylistDataAccessObject);
        views.add(initialView, initialView.viewName);

        viewManagerModel.setActiveView("page 1");
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
