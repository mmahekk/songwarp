package data_access;

import entity.Playlist;
import entity.Song;
import use_case.view_playlist.ViewPlaylistDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistDataAccessObject implements ViewPlaylistDataAccessInterface {

    @Override
    public List<String> getPlaylistData(Playlist playlist) {

        List<String> playlistData = new ArrayList<>();

        if (playlist != null) {
            playlistData.add("Playlist Name: " + playlist.getName());

            List<Song> songs = playlist.getList();
            for (Song song : songs) {
                // Add each song as a separate line
                String songInfo = song.getName() + " by " + song.getAuthor();
                playlistData.add(songInfo);
            }
        } else {
            playlistData.add("Invalid playlist");

        }

        return playlistData;
    }

}