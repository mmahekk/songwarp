package entity;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubePlaylist extends Playlist implements YoutubePlaylistInterface {
    private final String youtubeID;

    public YoutubePlaylist(String name, String genre, String youtubeID) {
        super(name, genre);
        this.youtubeID = youtubeID;
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

    public String getYoutubeID() {
        return youtubeID;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.append("youtubeID", this.getYoutubeID());
        return jsonObject;
    }
}