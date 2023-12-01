package data_access;

import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import entity.Song;
import use_case.view_playlist.ViewPlaylistDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistDataAccessObject implements ViewPlaylistDataAccessInterface {

    @Override
    public List<String> getPlaylistData(Playlist playlist) {

        List<String> playlistData = new ArrayList<>();

        if (playlist != null && playlist instanceof CompletePlaylist) {

            CompletePlaylist completePlaylist = (CompletePlaylist) playlist;

            playlistData.add("Playlist Name: " + completePlaylist.getName());

            List<CompleteSong> songs = completePlaylist.getCompleteSongs();

            for (CompleteSong song : songs) {

                // Extract complete song information
                String name = song.getName();
                String author = song.getAuthor();
                String date = song.getDate();
                String youtubeID = song.getYoutubeId();
                String spotifyID = song.getSpotifyId();
                String youtubeTitle = song.getYoutubeTitle();
                String youtubeChannel = song.getYoutubeChannel();

                String songInfo = name + " by " + author + " released " + date + ", YoutubeID: " +
                        youtubeID + ", SpotifyID: " + spotifyID + ", Youtube Title: " +
                        youtubeTitle + ", Youtube Channel Name: " + youtubeChannel;

                playlistData.add(songInfo);
            }
        } else {
            playlistData.add("Invalid playlist");

        }

        return playlistData;
    }

}