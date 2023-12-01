package entity;

import org.json.JSONObject;

public class CompleteSong extends Song {
    private final String spotifyID;
    private final String youtubeID;
    private final String youtubeTitle;
    private final String youtubeChannel;
    private final int duration;

    public CompleteSong(String title, String author, String spotifyID,
                        String youtubeID, String date,
                        String youtubeTitle, String youtubeChannel, int duration) {
        super(title, author, date);
        this.spotifyID = spotifyID;
        this.youtubeID = youtubeID;
        this.youtubeTitle = youtubeTitle;
        this.youtubeChannel = youtubeChannel;
        this.duration = duration;
    }

    public String getSpotifyId() {
        return this.spotifyID;
    }

    public String getYoutubeId() {
        return this.youtubeID;
    }

    public int getDuration() {
        return this.duration;
    }

    public String getYoutubeTitle() {
        return this.youtubeTitle;
    }

    public  String getYoutubeChannel() {
        return this.youtubeChannel;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", this.getName());
        jsonObject.append("author", this.getAuthor());
        jsonObject.append("date", this.getDate());
        jsonObject.append("duration", this.getDuration());
        jsonObject.append("spotifyID", this.getSpotifyId());
        jsonObject.append("youtubeID", this.getYoutubeId());
        jsonObject.append("youtubeTitle", youtubeTitle);
        jsonObject.append("youtubeChannel", youtubeChannel);
        jsonObject.append("type", "CompleteSong");
        return jsonObject;
    }
}
