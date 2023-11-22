package view;

import interface_adapter.GetPlaylistState;
import interface_adapter.GetPlaylistViewModel;

import interface_adapter.load_playlist.LoadPlaylistController;
import interface_adapter.spotify_get.SpotifyGetController;
import interface_adapter.youtube_get.YoutubeGetController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class InitialView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 1";
    private final GetPlaylistViewModel getPlaylistViewModel;
    private final JTextField urlInputField = new JTextField(40);
    private final YoutubeGetController youtubeGetController;
    private final SpotifyGetController spotifyGetController;
//    private final LoadPlaylistController loadPlaylistController;

    private final JButton youtubeGet;
    private final JButton spotifyGet;
    private final JButton loadPlaylist;

    public InitialView(GetPlaylistViewModel getPlaylistViewModel,
                       YoutubeGetController youtubeGetController,
                       SpotifyGetController spotifyGetController,
                       LoadPlaylistController loadPlaylistController
                       ) {
        this.getPlaylistViewModel = getPlaylistViewModel;
        this.youtubeGetController = youtubeGetController;
        this.spotifyGetController = spotifyGetController;
//        this.loadPlaylistController = loadPlaylistController;

        getPlaylistViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(getPlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel urlInput = new LabelTextPanel(
                new JLabel(getPlaylistViewModel.URL_LABEL), urlInputField);

        JPanel buttons = new JPanel();
        youtubeGet = new JButton(getPlaylistViewModel.YOUTUBEGET_BUTTON_LABEL);
        buttons.add(youtubeGet);
        spotifyGet = new JButton(getPlaylistViewModel.SPOTIFYGET_BUTTON_LABEL);
        buttons.add(spotifyGet);
        loadPlaylist = new JButton(getPlaylistViewModel.LOADPLAYLIST_BUTTON_LABEL);
        buttons.add(loadPlaylist);

        loadPlaylist.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(loadPlaylist)) {
                            //right now it defaults to open src folder as starting folder
                            //can change it to default by removing the argument
                            JFileChooser fileChooser = new JFileChooser("src");

                            //sets a filter for possible extensions
                            FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
                            fileChooser.setFileFilter(filter);

                            int result = fileChooser.showOpenDialog(null);

                            if (result == JFileChooser.APPROVE_OPTION) {
                                java.io.File selectedFile = fileChooser.getSelectedFile();

                                // Get the absolute path of the selected file and set it in the text field
                                String filePath = selectedFile.getAbsolutePath();
                                loadPlaylistController.execute(filePath);
                            }
                        }
                    }
                }
        );

        youtubeGet.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GetPlaylistState currentState = getPlaylistViewModel.getState();
                    if (e.getSource().equals(youtubeGet)) {
                        youtubeGetController.execute(currentState.getUrlInput());
                    }
                }
            }
        );
        spotifyGet.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GetPlaylistState currentState = getPlaylistViewModel.getState();
                        if (e.getSource().equals(spotifyGet)) {
                            spotifyGetController.execute(currentState.getUrlInput());
                        }
                    }
                }
        );

        urlInputField.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    GetPlaylistState currentState = getPlaylistViewModel.getState();
                    String text = urlInputField.getText() + e.getKeyChar();
                    currentState.setUrlInput(text);
                    getPlaylistViewModel.setState(currentState);
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(urlInput);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showConfirmDialog(this, "not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();
        System.out.println("Property change caught: " + newValue.toString());
        if (newValue instanceof GetPlaylistState state) {
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }
}


