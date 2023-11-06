package entity;

import java.util.ArrayList;

public class SpotifySong extends Song implements SpotifyInterface {
    private final String spotifyURL;
    private final ArrayList<String> genre;

    public SpotifySong(String title, String author, int duration, String spotifyURL, ArrayList<String> genre) {
        super(title, author, duration);
        this.spotifyURL = spotifyURL;
        this.genre = genre;
    }

    public String getSpotifyURL() {
        return this.spotifyURL;
    }
    public ArrayList<String> getGenre() {
        return this.genre;
    }
}