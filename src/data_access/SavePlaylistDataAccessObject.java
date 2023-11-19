package data_access;
import entity.CompletePlaylist;
import entity.CompleteSong;
import entity.Playlist;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.save_playlist.SavePlaylistDataAccessInterface;
import use_case.save_playlist.SavePlaylistInputData;

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
        String filePath = inputData.getFilePath();

        // Convert playlist to well-formatted JSON with URLs
        JSONObject jsonObject = convertPlaylistToJSON((CompletePlaylist) playlist);

        // Save JSON to file
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonObject.toString(2)); // Use 2-space indentation for better formatting
            System.out.println("Playlist saved to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
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