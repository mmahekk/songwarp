package data_access;

import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import use_case.view_playlist.ViewPlaylistDataAccessInterface;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ViewPlaylistDataAccessObject implements ViewPlaylistDataAccessInterface {

    // gets all the playlist data needed for the output
    @Override
    public List<String> getPlaylistData(Playlist playlist) {
        List<String> playlistData = new ArrayList<>();

        if (playlist != null && playlist instanceof CompletePlaylist) {
            CompletePlaylist completePlaylist = (CompletePlaylist) playlist;

            List<CompleteSong> songs = completePlaylist.getCompleteSongs();

            for (CompleteSong song : songs) {
                String formattedSongInfo = formatSongInfo(song);
                playlistData.add(formattedSongInfo);
            }
        } else {
            playlistData.add("Invalid playlist");
        }

        return playlistData;
    }

    // helper function for getPlaylistData which formats the song information to be displayed line by line
    @Override
    public String formatSongInfo(CompleteSong song) {

        // Extract complete song information

        String name = song.getName();
        String author = song.getAuthor();
        String date = song.getDate();
        String youtubeID = song.getYoutubeId();
        String spotifyID = song.getSpotifyId();
        String youtubeTitle = song.getYoutubeTitle();
        String youtubeChannel = song.getYoutubeChannel();
        int duration = song.getDuration();;
        String spotifyInfo = author + " - " + name;
        String spotifyLink = "Spotify Link: https://open.spotify.com/track/" + spotifyID;
        String youtubeLink = "YouTube Link: https://www.youtube.com/watch?v=" + youtubeID;
        String youtubeInfo = "YouTube Title: " + youtubeTitle + " [from " + youtubeChannel + "]";
        String dateFormat = "Date Released: " + formatDate(date);

        String formatDuration = "Duration: " + convertDuration(duration) + " minutes";

        return spotifyInfo + "\n" + spotifyLink + "\n" +
                youtubeLink + "\n" + youtubeInfo + "\n" +
                dateFormat + "\n" + formatDuration + "\n";
    }


    // helper function for formatSongInfo that converts the duration from milliseconds to minutes
    @Override
    public String convertDuration(int durationInMillis) {
        int seconds = (durationInMillis / 1000) % 60;
        int minutes = (durationInMillis / (1000 * 60));

        return String.format("%d:%02d", minutes, seconds);
    }


    // helper function for formatSongInfo that formats the date into yyyy-mm-dd format
    @Override
    public String formatDate(String dateString) {
        try {
            Instant instant = Instant.parse(dateString);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDateTime.format(formatter);
        } catch (DateTimeParseException e) {
            // If parsing as Instant fails (for Spotify), handle it differently
            return dateString;
        }
    }
}