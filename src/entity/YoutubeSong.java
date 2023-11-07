package entity;

import org.json.JSONObject;

public class YoutubeSong extends Song implements YoutubeSongInterface {

    private final String youtubeID;

    public YoutubeSong(String title, String author, String youtubeID, String date) {
        super(title, author, date);
        this.youtubeID = youtubeID;
    }

    public String getYoutubeID() {
        return this.youtubeID;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", this.getName());
        jsonObject.append("author", this.getAuthor());
        jsonObject.append("date", this.getDate());
        jsonObject.append("youtubeID", youtubeID);
        jsonObject.append("type", "YoutubeSong");
        return jsonObject;
    }
}
