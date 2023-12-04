package data_access;

import entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.load_playlist.LoadPlaylistDataAccessInterface;
import view.InitialView;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.prefs.Preferences;

import static data_access.TempFileWriterDataAccessObject.readTempJSON;

public class LoadPlaylistDataAccessObject implements LoadPlaylistDataAccessInterface {
    private String filepath;

    public void SetFilePath(String filepath) {
        this.filepath = filepath;
    }
    public String FetchFilePath() {
        return this.filepath;
    }

    public String GetFilePath() {
        JFileChooser fileChooser = new JFileChooser("src");
        Preferences prefs = Preferences.userNodeForPackage(InitialView.class);
        String lastOpenedFilePath = prefs.get("lastOpenedFilePath", null);

        // Set the initially selected file to the parent folder of the most recently opened file
        if (lastOpenedFilePath != null) {
            File lastOpenedFile = new File(lastOpenedFilePath);
            if (lastOpenedFile.exists() && lastOpenedFile.isFile()) {
                fileChooser.setSelectedFile(lastOpenedFile.getAbsoluteFile());
            }
        }

        //sets a filter for possible extensions
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("SongWarp Saved Files", "SWsave");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter1);
        fileChooser.addChoosableFileFilter(filter2);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            prefs.put("lastOpenedFilePath", selectedFile.getAbsolutePath());

            // Get the absolute path of the selected file and set it in the text field
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
    public YoutubePlaylist LoadYoutubePlaylist(String file) {
        PlaylistBuilderDirector director = new PlaylistBuilderDirector();
        YoutubePlaylistBuilder builder = new YoutubePlaylist.Builder();
        director.BuildYoutubePlaylist(builder, file);
        return builder.build();
    }

    public SpotifyPlaylist LoadSpotifyPlaylist(String file) {
        PlaylistBuilderDirector director = new PlaylistBuilderDirector();
        SpotifyPlaylistBuilder builder = new SpotifyPlaylist.Builder();
        director.BuildSpotifyPlaylist(builder, file);
        return builder.build();
    }

    public CompletePlaylist LoadCompletePlaylist(String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        if (jsonObject.has("type")) {
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

//    public Playlist LoadPlaylist(String file) {
//        JSONObject jsonObject = readTempJSON(file, false);
//        assert jsonObject != null;
//        if (jsonObject.has("type")) {
//            String type = (String) jsonObject.getJSONArray("type").get(0);
//            switch (type) {
//                case "entity.YoutubePlaylist" -> this.LoadYoutubePlaylist(file);
//                case "entity.SpotifyPlaylist" -> this.LoadSpotifyPlaylist(file);
//                case "entity.CompletePlaylist" -> this.LoadCompletePlaylist(file);
//            }
//        }
//        return null;
//    }
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