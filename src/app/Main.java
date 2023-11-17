package app;

import data_access.TempPlaylistDataAccessObject;
import data_access.YoutubeGetDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.youtube_get.YoutubeGetController;
import interface_adapter.youtube_get.YoutubeGetPresenter;
import interface_adapter.youtube_match.YoutubeMatchController;
import interface_adapter.youtube_match.YoutubeMatchPresenter;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
import use_case.youtube_get.YoutubeGetInteractor;
import use_case.youtube_get.YoutubeGetOutputBoundary;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import use_case.youtube_match.YoutubeMatchInteractor;
import use_case.youtube_match.YoutubeMatchOutputBoundary;
import view.InitialView;
import view.MatchOrSplitSelectionView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

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

        GetPlaylistViewModel getPlaylistViewModel = new GetPlaylistViewModel();
        ProcessPlaylistViewModel processPlaylistViewModel = new ProcessPlaylistViewModel();

        // temporary file writers
        TempPlaylistDataAccessObject fileWriter = new TempPlaylistDataAccessObject("temp.json");
        TempPlaylistDataAccessObject backupFileWriter = new TempPlaylistDataAccessObject("backup.json");

        //TODO: note, the following code is temporary and should be replaced once usecase factories are made
        YoutubeGetDataAccessInterface dataAccessObject = new YoutubeGetDataAccessObject();
        YoutubeGetOutputBoundary outputBoundary = new YoutubeGetPresenter(viewManagerModel, getPlaylistViewModel, processPlaylistViewModel);
        YoutubeGetInteractor youtubeGetInteractor = new YoutubeGetInteractor(dataAccessObject, fileWriter, outputBoundary);
        YoutubeGetController youtubeGetController = new YoutubeGetController(youtubeGetInteractor);
        InitialView initialView = new InitialView(getPlaylistViewModel, youtubeGetController);
//          InitialView initialView = GetPlaylistUseCaseFactory.create(
//          viewManagerModel, getPlaylistViewModel, tempPlaylistDataAccessObject);
        views.add(initialView, initialView.viewName);  // viewName is "page 1"


        YoutubeMatchDataAccessInterface matchdataAccessObject = new YoutubeMatchDataAccessObject();
        YoutubeMatchOutputBoundary matchoutputBoundary = new YoutubeMatchPresenter(viewManagerModel, processPlaylistViewModel);
        YoutubeMatchInteractor youtubeMatchInteractor = new YoutubeMatchInteractor(matchdataAccessObject, fileWriter, backupFileWriter, matchoutputBoundary);
        YoutubeMatchController youtubeMatchController = new YoutubeMatchController(youtubeMatchInteractor);
        MatchOrSplitSelectionView matchOrSplitSelectionView = new MatchOrSplitSelectionView(processPlaylistViewModel, youtubeMatchController);
//          MatchOrSplitSelectionView matchOrSplitSelectionView = ProcessPlaylistUseCaseFactory.create(
//          viewManagerModel, processPlaylistViewModel, tempPlaylistDataAccessObject);
        views.add(matchOrSplitSelectionView, matchOrSplitSelectionView.viewName);

        viewManagerModel.setActiveView(initialView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
