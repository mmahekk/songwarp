package view;

import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;
import interface_adapter.ProcessPlaylistState;
import interface_adapter.ProcessPlaylistViewModel;
import interface_adapter.spotify_match.SpotifyMatchController;
import interface_adapter.youtube_match.YoutubeMatchController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MatchOrSplitSelectionView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "page 2";
    private final ProcessPlaylistViewModel processPlaylistViewModel;
//    private final SpotifyMatchController spotifyMatchController;
//    private final SpotifyGenresplitController spotifyGenresplitController;
    private final YoutubeMatchController youtubeMatchController;
//    private final YoutubeGenresplitController youtubeGenresplitController;

    private final JButton match;
    private final JButton split;

    public MatchOrSplitSelectionView(ProcessPlaylistViewModel processPlaylistViewModel,
                                     // SpotifyMatchController spotifyMatchController,
                                     YoutubeMatchController youtubeMatchController) {
        this.processPlaylistViewModel = processPlaylistViewModel;
        // this.spotifyMatchController = spotifyMatchController;
        this.youtubeMatchController = youtubeMatchController;

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
                            MatchOrSplitSelectionView.this.youtubeMatchController.execute(playlist);
                        } else if (currentState.getPlaylist() instanceof SpotifyPlaylist playlist) {
                            // MatchOrSplitSelectionView.this.spotifyMatchController.execute(playlist);
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
            }
        }
    }
}
