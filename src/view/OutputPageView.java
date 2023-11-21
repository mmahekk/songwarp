package view;

import data_access.TempFileWriterDataAccessObject;
import entity.*;
import interface_adapter.*;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.view_traverse.ViewTraverseController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OutputPageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 3";
    private final PutPlaylistViewModel putPlaylistViewModel;
    private final GetPlaylistViewModel getPlaylistViewModel;

//    private final YoutubePutController youtubePutController;
//    private final SpotifyPutController spotifyPutController;
    private final SavePlaylistController savePlaylistController;
    private final ViewTraverseController viewTraverseController;

    private final JButton save;
    private final JButton spotifyPut;
    private final JButton youtubePut;
    private final JButton viewPlaylist;
    private final JButton restart;
    private final JTextField namePlaylistInputField;
    private final JTextArea playlistView;

    public OutputPageView(PutPlaylistViewModel putPlaylistViewModel,
                         GetPlaylistViewModel getPlaylistViewModel,
                         SavePlaylistController savePlaylistController,
                          ViewTraverseController viewTraverseController) {
        this.putPlaylistViewModel = putPlaylistViewModel;
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.savePlaylistController = savePlaylistController;
        this.viewTraverseController = viewTraverseController;
        // this.controller = controller initialize controllers

        putPlaylistViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(putPlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        namePlaylistInputField = new JTextField(40);
        playlistView = new JTextArea();

        LabelTextPanel playlistNameInput = new LabelTextPanel(
                new JLabel(putPlaylistViewModel.SAVE_PLAYLIST_TITLE), namePlaylistInputField);
        LabelTextPanel playlistViewPanel = new LabelTextPanel(
                new JLabel(putPlaylistViewModel.VIEW_OUTPUT_LABEL), playlistView);

        JPanel buttons = new JPanel();
        save = new JButton(putPlaylistViewModel.SAVE_PLAYLIST_BUTTON_LABEL);
        buttons.add(save);
        youtubePut = new JButton(putPlaylistViewModel.YOUTUBE_PUT_BUTTON_LABEL);
        buttons.add(youtubePut);
        spotifyPut = new JButton(putPlaylistViewModel.SPOTIFY_PUT_BUTTON_LABEL);
        buttons.add(spotifyPut);
        viewPlaylist = new JButton(putPlaylistViewModel.VIEW_PLAYLIST_BUTTON_LABEL);
        buttons.add(viewPlaylist);
        restart = new JButton(putPlaylistViewModel.RESTART_BUTTON_LABEL);
        buttons.add(restart);

        save.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PutPlaylistState currentState = putPlaylistViewModel.getState();
                    if (e.getSource().equals(save)) {
                        Playlist playlist = currentState.getPlaylist();
                        CompletePlaylist incompletePlaylist = currentState.getIncompletePlaylist();
                        if (playlist == null) {
                            TempFileWriterDataAccessObject fileWriter = new TempFileWriterDataAccessObject("temp.json");
                            playlist = fileWriter.readPlaylistJSON();
                        }
                        String filepath = currentState.getPlaylistName();
                        savePlaylistController.execute(filepath, playlist, incompletePlaylist);
                    }
                }
            }
        );

        restart.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource().equals(restart)) {
                        viewTraverseController.execute();
                    }
                }
            }
        );

        namePlaylistInputField.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    PutPlaylistState currentState = putPlaylistViewModel.getState();
                    String text = namePlaylistInputField.getText() + e.getKeyChar();
                    currentState.setPlaylistName(text);
                    putPlaylistViewModel.setState(currentState);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(playlistNameInput);
        this.add(playlistViewPanel);

        this.add(title);
        this.add(buttons);
    }

    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        System.out.println("Property change caught: " + newValue.toString());
        if (newValue instanceof PutPlaylistState state) {
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }


    public SavePlaylistController getSavePlaylistController() {
        return this.savePlaylistController;
    }

    public ViewTraverseController getViewTraverseController() {
        return this.viewTraverseController;
    }
}
