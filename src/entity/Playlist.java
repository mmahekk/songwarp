package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class Playlist implements PlaylistInterface {
    private final String name;
    private ArrayList<Song> songs;
    private String genre;

    public Playlist(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.songs = new ArrayList<>();
    }

    @Override
    public ArrayList<Song> getList() {
        return songs;
    }

//    @Override
//    public int getDuration() {
//        int totalDuration = 0;
//        for (Song song : songs) {
//            totalDuration += song.getDuration();
//        }
//        return totalDuration;
//    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGenre() {
        return genre;  // Return the playlist's own genre
    }

    @Override
    public int getTotal() {
        return songs.size();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

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
        jsonObject.append("items", songList);
        jsonObject.append("type", this.getClass().getName());
        return jsonObject;
    }
}