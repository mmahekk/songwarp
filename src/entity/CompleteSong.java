package entity;

import java.util.ArrayList;

public class CompleteSong extends Song {
    private final String spotifyID;
    private final String youtubeID;
    private final String youtubeTitle;
    private final String youtubeChannel;
    private final ArrayList<String> genre;

    public CompleteSong(String title, String author, String spotifyID, 
                        String youtubeID, ArrayList<String> genre, String date, String youtubeTitle, String youtubeChannel) {
        super(title, author, date);
        this.spotifyID = spotifyID;
        this.youtubeID = youtubeID;
        this.youtubeTitle = youtubeTitle;
        this.youtubeChannel = youtubeChannel;
        this.genre = genre;
    }

}
