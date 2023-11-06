package entity;

import java.util.ArrayList;

public class CompleteSong extends Song {
    private final String spotifyURL;
    private final String youtubeURL;
    private final ArrayList<String> genre;

    public CompleteSong(String title, String author, int duration,
                        String spotifyURL,String youtubeUrl, ArrayList<String> genre) {
        super(title, author, duration);
        this.spotifyURL = spotifyURL;
        this.youtubeURL = youtubeUrl;
        this.genre = genre;
    }

}
