package entity;

public class YoutubeSong extends Song implements YoutubeInterface {

    private final String youtubeURL;

    public YoutubeSong(String title, String author, String date, String youtubeURL) {
        super(title, author, date);
        this.youtubeURL = youtubeURL;
    }

    public String getYoutubeURL() {
        return this.youtubeURL;
    }
}
