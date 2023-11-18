package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompletePlaylist extends Playlist {
    private String name;
    private ArrayList<Song> songs;
    private String genre;
    private String youtubeID;
    private String spotifyID;

    public CompletePlaylist(String name, String genre, String youtubeID, String spotifyID) {
        super(name, genre);
        this.songs = new ArrayList<>();
        this.youtubeID = youtubeID;
        this.spotifyID = spotifyID;
    }

    public String[] getIDs() {
        return new String[]{youtubeID, spotifyID};
    }

    public void setYoutubeID(String youtubeID) {
        this.youtubeID = youtubeID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public int getDuration() {
        int totalDuration = 0;
        for (CompleteSong song : this.getCompleteSongs()) {
            totalDuration += song.getDuration();
        }
        return totalDuration;
    }

    public ArrayList<CompleteSong> getCompleteSongs() {
        // Implement the logic to filter and return Spotify songs from the playlist
        ArrayList<CompleteSong> completeSongs = new ArrayList<>();
        for (Song song : this.getList()) {
            if (song instanceof CompleteSong) {
                completeSongs.add((CompleteSong) song);
            }
        }
        return completeSongs;
    }

    @Override
    public ArrayList<Song> getList() {
        return super.getList();
    }

    public void setName() {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public int getTotal() {
        return songs.size();
    }

    @Override
    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public void removeSong(Song song) {
        songs.remove(song);
    }

    @Override
    public void setGenre(String genre) {
        this.genre = genre;
    }


    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        JSONArray songList = new JSONArray();
        for (Song value : songs) {
            JSONObject song = value.convertToJSON();
            songList.put(song);
        }
        jsonObject.put("items", songList);
        jsonObject.put("type", this.getClass().getName());
        return jsonObject;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}