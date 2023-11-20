package entity;

import org.json.JSONObject;

import java.util.ArrayList;

public class SpotifySong extends Song implements SpotifySongInterface {
    private final int duration;
    private final String spotifyID;
    public SpotifySong(String title, String author, int duration, String spotifyID, String date) {
        super(title, author, date);
        this.duration = duration;
        this.spotifyID = spotifyID;
    }

    public String getSpotifyID() {
        return this.spotifyID;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", this.getName());
        jsonObject.append("author", this.getAuthor());
        jsonObject.append("date", this.getDate());
        jsonObject.append("spotifyID", this.getSpotifyID());
        jsonObject.append("duration", this.getDuration());
        jsonObject.append("type", "SpotifySong");
        return jsonObject;
    }
}