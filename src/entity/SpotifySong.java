package entity;

import org.json.JSONObject;

import java.util.ArrayList;

public class SpotifySong extends Song implements SpotifySongInterface {
    private final int duration;
    private final String spotifyID;
    private final ArrayList<String> genre;
    public SpotifySong(String title, String author, int duration, String spotifyID, ArrayList<String> genre, String date) {
        super(title, author, date);
        this.duration = duration;
        this.spotifyID = spotifyID;
        this.genre = genre;
    }

    public String getSpotifyID() {
        return this.spotifyID;
    }
    public ArrayList<String> getGenre() {
        return this.genre;
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
        jsonObject.append("spotifyID", spotifyID);
        jsonObject.append("genres", genre);
        jsonObject.append("type", "SpotifySong");
        return jsonObject;
    }
}