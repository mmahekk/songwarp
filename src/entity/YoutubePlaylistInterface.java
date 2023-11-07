package entity;

import java.util.ArrayList;

public interface YoutubePlaylistInterface {
    ArrayList<YoutubeSong> getYoutubeSongs(); // Returns a list of YouTube songs
    String getYoutubeID(); // Returns the YouTube URL for the playlist
}