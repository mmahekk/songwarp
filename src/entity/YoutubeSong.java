package entity;

public class YoutubeSong extends Song implements YoutubeInterface {

    private final String youtubeID;

    public YoutubeSong(String title, String author, String youtubeID, String date) {
        super(title, author, date);
        this.youtubeID = youtubeID;
    }

    public String getYoutubeID() {
        return this.youtubeID;
    }
}
