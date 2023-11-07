package entity;

import java.util.ArrayList;

public class CompleteSong extends Song {
    private final String spotifyId;
    private final String youtubeId;
    private final ArrayList<String> genre;

    public CompleteSong(String title, String author, String date,
                        String spotifyId,String youtubeId, ArrayList<String> genre) {
        super(title, author, date);
        this.spotifyId = spotifyId;
        this.youtubeId = youtubeId;
        this.genre = genre;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public String getYoutubeId() {
        return this.youtubeId;
    }

    public ArrayList<String> getGenres() {
        return this.genre;
    }
}
