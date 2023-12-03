package app;

import data_access.TempFileWriterDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import view.InitialView;
import view.MatchOrSplitSelectionView;
import view.OutputPageView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("SongWarp - A YouTube-Spotify Playlist converter");
        ImageIcon icon = new ImageIcon("songwarpLogo2.png");
        application.setIconImage(icon.getImage());
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // make the view models
        GetPlaylistViewModel getPlaylistViewModel = new GetPlaylistViewModel();
        ProcessPlaylistViewModel processPlaylistViewModel = new ProcessPlaylistViewModel();
        PutPlaylistViewModel putPlaylistViewModel = new PutPlaylistViewModel();

        // temporary file writers
        TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
        TempFileWriterDataAccessObject backupFileWriter = new TempFileWriterDataAccessObject("backup.json");

        // create the views
        InitialView initialView = GetPlaylistUseCaseFactory.create(
                viewManagerModel, getPlaylistViewModel, processPlaylistViewModel, putPlaylistViewModel, fileWriter);
        assert initialView != null;
        views.add(initialView, initialView.viewName);  // viewName is "page 1"

        OutputPageView outputPageView = PutPlaylistUseCaseFactory.create(
                viewManagerModel, putPlaylistViewModel, processPlaylistViewModel, getPlaylistViewModel, fileWriter);
        assert outputPageView != null;
        views.add(outputPageView, outputPageView.viewName);

        MatchOrSplitSelectionView matchOrSplitSelectionView = ProcessPlaylistUseCaseFactory.create(
                viewManagerModel, processPlaylistViewModel, putPlaylistViewModel, fileWriter, backupFileWriter,
                outputPageView.getSavePlaylistController(), outputPageView.getViewTraverseController());
        assert matchOrSplitSelectionView != null;
        views.add(matchOrSplitSelectionView, matchOrSplitSelectionView.viewName);

        viewManagerModel.setActiveView(initialView.viewName);
        viewManagerModel.firePropertyChanged();
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        application.pack();
        application.setVisible(true);
    }
}
