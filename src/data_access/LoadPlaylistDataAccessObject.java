package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;

import static data_access.TempFileWriterDataAccessObject.readTempJSON;

public class LoadPlaylistDataAccessObject implements LoadPlaylistDataAccessInterface {

    public YoutubePlaylist LoadYoutubePlaylist(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);

        String youtubeID = jsonObject.getJSONArray("youtubeID").getString(0);
        YoutubePlaylist youtubePlaylist = new YoutubePlaylist("loaded playlist", null, youtubeID);
        for (int i = 0; i < songList.length(); i++) {
            JSONObject entry = songList.getJSONObject(i);
            String title = entry.getJSONArray("name").getString(0);
            String author = entry.getJSONArray("author").getString(0);
            String date = entry.getJSONArray("date").getString(0);
            String id = entry.getJSONArray("youtubeID").getString(0);

            YoutubeSong song = new YoutubeSong(title, author, id, date);
            youtubePlaylist.addSong(song);
        }
        return youtubePlaylist;
    }

    public SpotifyPlaylist LoadSpotifyPlaylist(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);

        String spotifyID = jsonObject.getJSONArray("spotifyID").getString(0);
        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist("loaded playlist", null, spotifyID);
        for (int i = 0; i < songList.length(); i++) {
            JSONObject entry = songList.getJSONObject(i);
            String title = entry.getJSONArray("name").getString(0);
            String author = entry.getJSONArray("author").getString(0);
            String date = entry.getJSONArray("date").getString(0);
            String id = entry.getJSONArray("spotifyID").getString(0);
            int duration = entry.getJSONArray("duration").getInt(0);

            SpotifySong song = new SpotifySong(title, author, duration, id, date);
            spotifyPlaylist.addSong(song);
        }
        return spotifyPlaylist;
    }

    public CompletePlaylist LoadCompletePlaylist(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        if (jsonObject.has("type") && jsonObject.has("type")) {
            JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);
            String youtubeID = jsonObject.getJSONArray("youtubeID").getString(0);
            String spotifyID = jsonObject.getJSONArray("spotifyID").getString(0);
            CompletePlaylist completePlaylist = new CompletePlaylist("loaded playlist", null, youtubeID, spotifyID);
            for (int i = 0; i < songList.length(); i++) {
                JSONObject entry = songList.getJSONObject(i);
                String title = entry.getJSONArray("name").getString(0);
                String channel = entry.getJSONArray("author").getString(0);
                String date = entry.getJSONArray("date").getString(0);
                String spotifySongID = entry.getJSONArray("spotifyID").getString(0);
                String youtubeSongID = entry.getJSONArray("youtubeID").getString(0);
                int duration = entry.getJSONArray("duration").getInt(0);
                String youtubeTitle = entry.getJSONArray("youtubeTitle").getString(0);
                String youtubeChannel = entry.getJSONArray("youtubeChannel").getString(0);
                CompleteSong song = new CompleteSong(title, channel, spotifySongID, youtubeSongID, date,
                        youtubeTitle, youtubeChannel, duration);
                completePlaylist.addSong(song);
            }
            return completePlaylist;
        }
        return null;
    }

    public Playlist LoadPlaylist(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        if (jsonObject.has("type") && jsonObject.has("type")) {
            String type = (String) jsonObject.getJSONArray("type").get(0);
            switch (type) {
                case "entity.YoutubePlaylist" -> this.LoadYoutubePlaylist(file);
                case "entity.SpotifyPlaylist" -> this.LoadSpotifyPlaylist(file);
                case "entity.CompletePlaylist" -> this.LoadCompletePlaylist(file);
            }
        }
        return null;
    }
    public String Type(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        if (jsonObject.has("type") && jsonObject.has("type")) {
            String type = (String) jsonObject.getJSONArray("type").get(0);
            switch (type) {
                case "entity.YoutubePlaylist" -> {
                    return "YoutubePlaylist";
                }
                case "entity.SpotifyPlaylist" -> {
                    return "SpotifyPlaylist";
                }
                case "entity.CompletePlaylist" -> {
                    return "CompletePlaylist";
                }
            }
        }
        return null;
    }
}