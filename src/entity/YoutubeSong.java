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
        jsonObject.append("youtubeID", this.getYoutubeID());
        jsonObject.append("type", "YoutubeSong");
        return jsonObject;
    }
    public static class Builder implements YoutubeSongBuilder{
        private String title;
        private String author;
        private String youtubeID;
        private String date;
        public void Title(String title) {
            this.title = title;
        }
        public void Author(String author) {
            this.author = author;
        }
        public void Id(String youtubeID) {
            this.youtubeID = youtubeID;
        }
        public void Date(String date) {
            this.date = date;
        }
        public YoutubeSong build() {
            return new YoutubeSong(title, author, youtubeID, date);
        }
    }
}
