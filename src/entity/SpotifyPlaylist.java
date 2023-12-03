package entity;

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
    public static class Builder implements SpotifyPlaylistBuilder {
        private SpotifyPlaylist playlist;

        @Override
        public void Playlist(SpotifyPlaylist playlist) {
            this.playlist = playlist;
        }

        public SpotifyPlaylist build() {
            return this.playlist;
        }
    }
}