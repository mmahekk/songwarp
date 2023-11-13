package app;

import interface_adapter.load_playlist.LoadPlaylistController;
import view.InitialView;

import javax.swing.*;
import java.io.IOException;

public class LoadPlaylistUseCaseFactory {

    private LoadPlaylistUseCaseFactory() {}

    //TODO: The structure of this class is created, but details need to be filled in when corresponding methods are implemented
    public static InitialView create() {
        try {
            LoadPlaylistController loadPlaylistController = createLoadUseCase();
            return new InitialView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load playlist");
        }
        return null;
    }

    private static LoadPlaylistController createLoadUseCase() throws IOException {


        return new LoadPlaylistController();
    }
}
