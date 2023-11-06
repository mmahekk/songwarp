package entity;
import java.util.ArrayList;

public interface SpotifyPlaylistInterface {
    ArrayList<SpotifySong> getSpotifySongs(); // Returns a list of Spotify songs
    String getSpotifyURL(); // Returns the Spotify URL for the playlist
}