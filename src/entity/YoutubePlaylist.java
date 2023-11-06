package entity;
import java.util.ArrayList;

public class YoutubePlaylist extends Playlist implements YoutubePlaylistInterface {
    private final String youtubeURL;

    public YoutubePlaylist(String name, String genre, String youtubeURL) {
        super(name, genre);
        this.youtubeURL = youtubeURL;
    }

    @Override
    public ArrayList<Song> getList() {
        return super.getList();
    }

    @Override
    public int getDuration() {
        return super.getDuration();
    }

    @Override
    public String getGenre() {
        return super.getGenre();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getTotal() {
        return super.getTotal();
    }

    @Override
    public ArrayList<YoutubeSong> getYoutubeSongs() {
        // Implement the logic to filter and return YouTube songs from the playlist
        ArrayList<YoutubeSong> youtubeSongs = new ArrayList<>();
        for (Song song : super.getList()) {
            if (song instanceof YoutubeSong) {
                youtubeSongs.add((YoutubeSong) song);
            }
        }
        return youtubeSongs;
    }

    @Override
    public String getYoutubeURL() {
        return youtubeURL;
    }
}