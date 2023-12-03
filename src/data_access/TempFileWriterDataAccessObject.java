package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TempFileWriterDataAccessObject {
    public String file;

    public TempFileWriterDataAccessObject(String file) {
        String filePath = "temp_jsons/" + file;
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                System.out.println("File created successfully.");
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        }
        this.file = filePath;
    }
    public void writePlaylistFile(Playlist playlist) {
        String filepath = file;
        JSONObject jsonObject = playlist.convertToJSON();
        try (FileWriter file = new FileWriter(filepath)) {
            file.write(jsonObject.toString());
            System.out.println("Playlist written to " + filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject readTempJSON(String file, boolean print) {
        String filePath;
        filePath = Objects.requireNonNullElse(file, "temp_jsons/temp.json");
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            if (!content.isEmpty()) {
                JSONObject jsonObject = new JSONObject(content);
                if (print) {
                    System.out.println(jsonObject.toString(2));
                }
                return jsonObject;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Playlist readPlaylistJSON() {
        JSONObject jsonObject = readTempJSON(file, false);
        if (jsonObject != null) {
            if (jsonObject.has("type") && jsonObject.has("type")) {
                String type = (String) jsonObject.getJSONArray("type").get(0);
                JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);
                switch (type) {
                    case "entity.YoutubePlaylist" -> {
                        String youtubeID = jsonObject.getJSONArray("youtubeID").getString(0);
                        YoutubePlaylist youtubePlaylist = new YoutubePlaylist("loaded playlist", null, youtubeID);
                        for (int i = 0; i < songList.length(); i++) {
                            JSONObject entry = songList.getJSONObject(i);
                            String title = entry.getJSONArray("name").getString(0);
                            String author = entry.getJSONArray("author").getString(0);
                            String date = entry.getJSONArray("date").getString(0);
                            String id = entry.getJSONArray("youtubeID").getString(0);

                            SongBuilderDirector director = new SongBuilderDirector();
                            YoutubeSongBuilder builder = new YoutubeSong.Builder();
                            director.BuildYoutubeSong(builder, title, author, id, date);
                            YoutubeSong song = builder.build();
                            youtubePlaylist.addSong(song);
                        }
                        return youtubePlaylist;
                    }
                    case "entity.SpotifyPlaylist" -> {
                        String spotifyID = jsonObject.getJSONArray("spotifyID").getString(0);
                        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist("loaded playlist", null, spotifyID);
                        for (int i = 0; i < songList.length(); i++) {
                            JSONObject entry = songList.getJSONObject(i);
                            String title = entry.getJSONArray("name").getString(0);
                            String author = entry.getJSONArray("author").getString(0);
                            String date = entry.getJSONArray("date").getString(0);
                            String id = entry.getJSONArray("spotifyID").getString(0);
                            int duration = entry.getJSONArray("duration").getInt(0);

                            SongBuilderDirector director = new SongBuilderDirector();
                            SpotifySongBuilder builder = new SpotifySong.Builder();
                            director.BuildSpotifySong(builder, title, author, duration, id, date);
                            SpotifySong song = builder.build();
                            spotifyPlaylist.addSong(song);
                            spotifyPlaylist.addSong(song);
                        }
                        return spotifyPlaylist;
                    }
                    case "entity.CompletePlaylist" -> {
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
                }
            }
        }
        return null;
    }
}
