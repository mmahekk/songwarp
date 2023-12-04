import app.GetPlaylistUseCaseFactory;
import app.ProcessPlaylistUseCaseFactory;
import app.PutPlaylistUseCaseFactory;
import data_access.TempFileWriterDataAccessObject;
import interface_adapter.GetPlaylistViewModel;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.PutPlaylistViewModel;
import interface_adapter.ViewManagerModel;
import org.junit.Test;
import view.InitialView;
import view.MatchOrSplitSelectionView;
import view.OutputPageView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static data_access.TempFileWriterDataAccessObject.readTempJSON;
import static utilities.YoutubeTitleInfoExtract.youtubeTitleInfoExtract;

public class TestViews {
    @Test
    public void testViews() {
        JFrame application = new JFrame("SongWarp - A YouTube-Spotify Playlist converter");
        ImageIcon icon = new ImageIcon("songwarpLogo2.png");
        application.setIconImage(icon.getImage());
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

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

        application.pack();
        application.setVisible(true);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        viewManagerModel.setActiveView(matchOrSplitSelectionView.viewName);
        viewManagerModel.firePropertyChanged();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        viewManagerModel.setActiveView(outputPageView.viewName);
        viewManagerModel.firePropertyChanged();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("worked");

    }
}
