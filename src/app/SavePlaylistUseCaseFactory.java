package app;

import interface_adapter.save_playlist.SavePlaylistController;
import view.OutputPageView;

import javax.swing.*;
import java.io.IOException;

public class SavePlaylistUseCaseFactory {
    private SavePlaylistUseCaseFactory() {}

    //TODO: The structure of this class is created, but details need to be filled in when corresponding methods are implemented
    public static OutputPageView create() {
        try {
            SavePlaylistController savePlaylistController = createSaveUseCase();
            return new OutputPageView();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "could not load playlist");
        }
        return null;
    }

    private static SavePlaylistController createSaveUseCase() throws IOException {


        return new SavePlaylistController();
    }
}
