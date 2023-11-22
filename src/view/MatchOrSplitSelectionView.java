package view;

import entity.CompletePlaylist;
import entity.Playlist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import interface_adapter.ProcessPlaylistState;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.save_playlist.SavePlaylistController;
import interface_adapter.view_traverse.ViewTraverseController;
import interface_adapter.youtube_match.YoutubeMatchController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MatchOrSplitSelectionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 2";
    private final ProcessPlaylistViewModel processPlaylistViewModel;

//    private final SpotifyMatchController spotifyMatchController;
//    private final SpotifyGenresplitController spotifyGenresplitController;
    private final YoutubeMatchController youtubeMatchController;
//    private final CompleteYearSplitController completeYearSplitController;

    private final SavePlaylistController savePlaylistController;
    private final ViewTraverseController viewTraverseController;

    private final JButton match;
    private final JButton split;

    public MatchOrSplitSelectionView(ProcessPlaylistViewModel processPlaylistViewModel,
                                     // SpotifyMatchController spotifyMatchController,
                                     // CompleteYearSplitController completeYearSplitController,
                                     YoutubeMatchController youtubeMatchController,
                                     SavePlaylistController savePlaylistController, ViewTraverseController viewTraverseController) {
        this.processPlaylistViewModel = processPlaylistViewModel;
        // this.spotifyMatchController = spotifyMatchController;
        // this.completeYearSplitController = completeYearSplitController;
        this.youtubeMatchController = youtubeMatchController;
        this.savePlaylistController = savePlaylistController;
        this.viewTraverseController = viewTraverseController;


        processPlaylistViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(processPlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        match = new JButton(processPlaylistViewModel.MATCH_BUTTON_LABEL);
        buttons.add(match);
        split = new JButton(processPlaylistViewModel.GENRESPLIT_BUTTON_LABEL);
        buttons.add(split);

        match.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProcessPlaylistState currentState = processPlaylistViewModel.getState();
                    if (e.getSource().equals(match)) {
                        if (currentState.getPlaylist() instanceof YoutubePlaylist playlist) {
                            Playlist incompletePlaylist = currentState.getIncompletePlaylist();
                            if (incompletePlaylist == null) {
                                youtubeMatchController.execute(playlist, true);
                            } else {
                                youtubeMatchController.execute(playlist, incompletePlaylist, true);
                            }
                        } else if (currentState.getPlaylist() instanceof SpotifyPlaylist playlist) {
                            // spotifyMatchController.execute(playlist, true);
                        }
                    }
                }
            }
        );

        split.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ProcessPlaylistState currentState = processPlaylistViewModel.getState();
                    if (e.getSource().equals(split)) {
                        if (currentState.getPlaylist() instanceof YoutubePlaylist playlist) {
                            // youtubeMatchController.execute(playlist, false);
                        } else if (currentState.getPlaylist() instanceof SpotifyPlaylist playlist) {
                            // spotifyMatchController.execute(playlist, false);
                        }
                        if (currentState.getPlaylist() instanceof CompletePlaylist playlist) {
                            // completeYearSplitController.execute(playlist)
                        }
                    }
                }
            }
        );


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
        if (newValue instanceof ProcessPlaylistState state) {
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
                if (state.getForcedToSave()) {
                    String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss", Locale.ENGLISH));
                    savePlaylistController.execute("Playlist in progress" + time, state.getIncompletePlaylist(), state.getPlaylist());
                    state.setForcedToSave(false);
                    viewTraverseController.execute();
                }
            }
        }
    }
}
