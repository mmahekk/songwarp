package data_access;
import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.save_playlist.SavePlaylistDataAccessInterface;
import use_case.save_playlist.SavePlaylistInputData;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SavePlaylistDataAccessObject implements SavePlaylistDataAccessInterface {

    @Override
    public Playlist getPlaylist(String filePath) {
        try {
            // Read the content of the JSON file
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);

            // Extract playlist information from the JSON object
            String playlistName = jsonObject.optString("name", "Default Playlist Name");
            String playlistGenre = jsonObject.optString("genre", "Default Genre");

            // Assuming your JSON structure includes a "songs" array
            JSONArray songsArray = jsonObject.optJSONArray("songs");
            ArrayList<CompleteSong> songs = new ArrayList<>();

            if (songsArray != null) {
                // Iterate through the songs array and create CompleteSong objects
                for (int i = 0; i < songsArray.length(); i++) {
                    JSONObject songObject = songsArray.getJSONObject(i);
                    // Extract song details from the JSON object
                    String title = songObject.optString("title", "Unknown Title");
                    String artist = songObject.optString("artist", "Unknown Artist");
                    String url = songObject.optString("url", "Unknown URL");
                    String spotifyID = songObject.optString("spotifyID", "Unknown Spotify ID");
                    String youtubeID = songObject.optString("youtubeID", "Unknown YouTube ID");
                    String youtubeTitle = songObject.optString("youtubeTitle", "Unknown YouTube Title");
                    String youtubeChannel = songObject.optString("youtubeChannel", "Unknown YouTube Channel");
                    int duration = songObject.optInt("duration", 0);

                    // Create a CompleteSong object and add it to the playlist
                    CompleteSong song = new CompleteSong(title, artist, url, spotifyID, youtubeID, youtubeTitle, youtubeChannel, duration);
                    songs.add(song);
                }
            }

            // Create and return a CompletePlaylist object
            CompletePlaylist completePlaylist = new CompletePlaylist(playlistName, playlistGenre, "youtubeID", "spotifyID");
//            completePlaylist.setSongs(songs);
            return completePlaylist;

        } catch (IOException | JSONException e) {
            // Handle exceptions (e.g., file not found, invalid JSON)
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }

    @Override
    public void createJSONFile(SavePlaylistInputData inputData) {
        Playlist playlist = inputData.getPlaylist();
        Playlist incompletePlaylist = inputData.getIncompletePlaylist();

        // Display file chooser to select the save location
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Select Folder to Save");

        // Set dialog mode to select directories only
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Show the file chooser dialog
        int returnValue = fileChooser.showDialog(null, "Save");

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected directory
            String selectedDirectory = fileChooser.getSelectedFile().getAbsolutePath();

            // Construct the file path using the selected directory and a default file name
            String fileName = inputData.getFilePath(); // or any other default file name
            String filePath = selectedDirectory + File.separator + fileName + ".SWsave";

            // Convert playlist to well-formatted JSON with URLs
            JSONObject jsonObject = convertPlaylistToJSON((CompletePlaylist) playlist);
            JSONObject jsonObject2;
            if (incompletePlaylist != null) {
                jsonObject2 = incompletePlaylist.convertToJSON();
            } else {
                jsonObject2 = null;
            }

            // Save JSON to file
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(jsonObject.toString(2)); // Use 2-space indentation for better formatting
                if (jsonObject2 != null) {
                    file.write(jsonObject2.toString(2));
                }
                System.out.println("Playlist saved to " + filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (returnValue == JFileChooser.CANCEL_OPTION) {
            System.out.println("User canceled the operation");
        }
    }

    private JSONObject convertPlaylistToJSON(CompletePlaylist playlist) {
        JSONObject jsonObject = new JSONObject();
        JSONArray songList = new JSONArray();

        // Implement logic to convert CompletePlaylist to JSON, including URLs
        for (CompleteSong song : playlist.getCompleteSongs()) {
            JSONObject songObject = song.convertToJSON();
            // Add additional attributes specific to CompleteSong if needed
            songList.put(songObject);
        }

        jsonObject.put("items", songList);
        jsonObject.put("type", playlist.getClass().getName());
        jsonObject.put("name", playlist.getName()); // Include playlist name in the JSON
        jsonObject.put("genre", playlist.getGenre()); // Include playlist genre in the JSON
        return jsonObject;
    }
}