package entity;
import org.json.JSONArray;
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
    public static class YoutubePlaylistBuilder {
        private final JSONArray songList;
        private final String playlistId;
        public YoutubePlaylistBuilder(JSONArray songList, String playlistId) {
            this.songList = songList;
            this.playlistId = playlistId;
        }
        public YoutubePlaylist build() {
            // convert JSON string to JSON object
            String name = "unknown name";

            // create empty youtubePlaylist object
            YoutubePlaylist youtubePlaylist = new YoutubePlaylist(name, null, playlistId);

            for (int i = 0; i < songList.length(); i++) {
                JSONObject entry = songList.getJSONObject(i);
                JSONObject snippet = entry.getJSONObject("snippet");

                if (snippet.has("videoOwnerChannelTitle")) {
                    String title = snippet.getString("title");
                    String channel = snippet.getString("videoOwnerChannelTitle");
                    String date = snippet.getString("publishedAt");
                    JSONObject extraInfo = snippet.getJSONObject("resourceId");
                    String id = extraInfo.getString("videoId");

                    YoutubeSong song = new YoutubeSong(title, channel, id, date);
                    youtubePlaylist.addSong(song);
                } else {
                    System.out.println("There was a deleted video here");
                }
            }
            return youtubePlaylist;
        }
    }
}