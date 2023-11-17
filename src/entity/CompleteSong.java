package entity;

import org.json.JSONObject;

import java.util.ArrayList;

public class CompleteSong extends Song {
    private final String spotifyID;
    private final String youtubeID;
    private final String youtubeTitle;
    private final String youtubeChannel;

    public CompleteSong(String title, String author, String spotifyID, 
                        String youtubeID, String date,
                        String youtubeTitle, String youtubeChannel) {
        super(title, author, date);
        this.spotifyID = spotifyID;
        this.youtubeID = youtubeID;
        this.youtubeTitle = youtubeTitle;
        this.youtubeChannel = youtubeChannel;
    }

    public String getSpotifyId() {
        return this.spotifyID;
    }

    public String getYoutubeId() {
        return this.youtubeID;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", this.getName());
        jsonObject.append("author", this.getAuthor());
        jsonObject.append("date", this.getDate());
        jsonObject.append("spotifyID", spotifyID);
        jsonObject.append("youtubeID", youtubeID);
        jsonObject.append("youtubeTitle", youtubeTitle);
        jsonObject.append("youtubeChannel", youtubeChannel);
        jsonObject.append("type", "CompleteSong");
        return jsonObject;
    }
}
