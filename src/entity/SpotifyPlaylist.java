package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpotifyPlaylist extends Playlist implements SpotifyPlaylistInterface {
    private final String spotifyID;

    public SpotifyPlaylist(String name, String genre, String spotifyID) {
        super(name, genre);
        this.spotifyID = spotifyID;
    }

    @Override
    public ArrayList<Song> getList() {
        return super.getList();
    }

    public int getDuration() {
        int totalDuration = 0;
        for (SpotifySong song : this.getSpotifySongs()) {
            totalDuration += song.getDuration();
        }
        return totalDuration;
    }

    @Override
    public String getName() {
        return super.getName();
    }
    @Override
    public String getGenre() {
        return super.getGenre();
    }

    @Override
    public int getTotal() {
        return super.getTotal();
    }

    @Override
    public ArrayList<SpotifySong> getSpotifySongs() {
        // Implement the logic to filter and return Spotify songs from the playlist
        ArrayList<SpotifySong> spotifySongs = new ArrayList<>();
        for (Song song : super.getList()) {
            if (song instanceof SpotifySong) {
                spotifySongs.add((SpotifySong) song);
            }
        }
        return spotifySongs;
    }

    @Override
    public String getSpotifyID() {
        return spotifyID;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = super.convertToJSON();
        jsonObject.append("spotifyID", this.getSpotifyID());
        return jsonObject;
    }
    public static class SpotifyPlaylistBuilder {

        private final String playlistId;
        private final JSONObject spotifyPlaylistJSON;

        public SpotifyPlaylistBuilder(JSONObject spotifyPlaylistJSON, String playlistId) {
            this.spotifyPlaylistJSON = spotifyPlaylistJSON;
            this.playlistId = playlistId;
        }
        public SpotifyPlaylist build() {
            if (spotifyPlaylistJSON.has("error")) {
                return null;
            } else {
                String name = "unknown name";
                JSONArray songlist = spotifyPlaylistJSON.getJSONObject("tracks").getJSONArray("items");

                SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist(name, null, playlistId);

                for (int i = 0; i < songlist.length(); i++) {
                    JSONObject entry = songlist.getJSONObject(i);
                    JSONObject snippet = entry.getJSONObject("track");

                    if (snippet.has("album")) {
                        JSONObject snippet2 = snippet.getJSONObject("album");
                        String title = snippet.getString("name");
                        String author = snippet2.getJSONArray("artists").getJSONObject(0).getString("name");
                        String date = snippet2.getString("release_date");
                        Integer duration = snippet.getInt("duration_ms");
                        String id = snippet.getString("id");

                        SpotifySong song = new SpotifySong(title, author, duration, id, date);
                        spotifyPlaylist.addSong(song);
                    } else {
                        System.out.println("Deleted.");
                    }
                }
                return spotifyPlaylist;
            }
        }
    }
}