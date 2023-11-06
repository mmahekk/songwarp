package entity;

public class YoutubeSong extends Song implements YoutubeInterface {

    private final String youtubeURL;

    public YoutubeSong(String title, String author, int duration, String youtubeURL) {
        super(title, author, duration);
        this.youtubeURL = youtubeURL;
    }

    public String getYoutubeURL() {
        return this.youtubeURL;
    }
}
