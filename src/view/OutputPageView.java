package view;

import data_access.TempFileWriterDataAccessObject;
import entity.*;
import interface_adapter.*;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.view_traverse.ViewTraverseController;
import interface_adapter.youtube_put.YoutubePutController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OutputPageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 3";
    private final PutPlaylistViewModel putPlaylistViewModel;
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final YoutubePutController youtubePutController;
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
    private final JLabel spotifyLink;
    private final JLabel youtubeLink;

    public OutputPageView(PutPlaylistViewModel putPlaylistViewModel, GetPlaylistViewModel getPlaylistViewModel,
                         SavePlaylistController savePlaylistController, ViewTraverseController viewTraverseController,
                          YoutubePutController youtubePutController) {
        this.putPlaylistViewModel = putPlaylistViewModel;
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.savePlaylistController = savePlaylistController;
        this.viewTraverseController = viewTraverseController;
        this.youtubePutController = youtubePutController;

        putPlaylistViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(putPlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        namePlaylistInputField = new JTextField(40);

        playlistView = new JTextArea(10, 100);
        playlistView.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(playlistView);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        LabelTextPanel textOutput = new LabelTextPanel(new JLabel(putPlaylistViewModel.VIEW_OUTPUT_LABEL), scrollPane);

        LabelTextPanel playlistNameInput = new LabelTextPanel(
                new JLabel(putPlaylistViewModel.SAVE_PLAYLIST_TITLE), namePlaylistInputField);
//        LabelTextPanel playlistViewPanel = new LabelTextPanel(
//                new JLabel(putPlaylistViewModel.VIEW_OUTPUT_LABEL), playlistView);

        JPanel links = new JPanel();
        spotifyLink = new JLabel("spotify link to be determined");
        spotifyLink.setForeground(Color.BLUE.darker());
        spotifyLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        links.add(spotifyLink);
        JLabel divider = new JLabel(" | ");
        links.add(divider);
        youtubeLink = new JLabel("youtube link to be determined");
        youtubeLink.setForeground(Color.BLUE.darker());
        youtubeLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        links.add(youtubeLink);

        JPanel buttons = new JPanel();
        viewPlaylist = new JButton(putPlaylistViewModel.VIEW_PLAYLIST_BUTTON_LABEL);
        viewPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        save = new JButton(putPlaylistViewModel.SAVE_PLAYLIST_BUTTON_LABEL);
        buttons.add(save);
        youtubePut = new JButton(putPlaylistViewModel.YOUTUBE_PUT_BUTTON_LABEL);
        buttons.add(youtubePut);
        spotifyPut = new JButton(putPlaylistViewModel.SPOTIFY_PUT_BUTTON_LABEL);
        buttons.add(spotifyPut);
        restart = new JButton(putPlaylistViewModel.RESTART_BUTTON_LABEL);
        restart.setAlignmentX(Component.CENTER_ALIGNMENT);

        spotifyLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!spotifyLink.getText().contains("to be determined")) {
                    try {
                        Desktop.getDesktop().browse(new URI(spotifyLink.getText()));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        youtubeLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!youtubeLink.getText().contains("to be determined")) {
                    try {
                        Desktop.getDesktop().browse(new URI(youtubeLink.getText()));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

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

        spotifyPut.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PutPlaylistState currentState = putPlaylistViewModel.getState();
                    if (e.getSource().equals(spotifyPut)) {
                        Playlist playlist = currentState.getPlaylist();
                        String name = currentState.getPlaylistName();
                        if (playlist instanceof CompletePlaylist p) {
                            youtubePutController.execute(p, name);
                        }
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

        this.add(title);
        this.add(playlistNameInput);
        this.add(textOutput, BorderLayout.CENTER);
        this.add(links);
        this.add(buttons);
        this.add(restart);
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
