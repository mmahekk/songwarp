package entity;

import java.util.ArrayList;

public class SpotifyPlaylist extends Playlist implements SpotifyPlaylistInterface {
    private final String spotifyURL;

    public SpotifyPlaylist(String name, String genre, String spotifyURL) {
        super(name, genre);
        this.spotifyURL = spotifyURL;
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
    public String getSpotifyURL() {
        return spotifyURL;
    }

}