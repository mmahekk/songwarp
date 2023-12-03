package entity;

import org.json.JSONObject;

public class SpotifySong extends Song implements SpotifySongInterface {
    private final int duration;
    private final String spotifyID;
    private SpotifySong(String title, String author, int duration, String spotifyID, String date) {
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
    public static class Builder implements SpotifySongBuilder{
        private String title;
        private String author;
        private int duration;
        private String spotifyID;
        private String date;
        public void Title(String title) {
            this.title = title;
        }
        public void Author(String author) {
            this.author = author;
        }
        public void Duration(int duration) {
            this.duration = duration;
        }
        public void Id(String spotifyID) {
            this.spotifyID = spotifyID;
        }
        public void Date(String date) {
            this.date = date;
        }
        public SpotifySong build() {
            return new SpotifySong(title, author, duration, spotifyID, date);
        }
    }
}