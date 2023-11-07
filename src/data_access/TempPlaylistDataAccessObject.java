package data_access;

import entity.Playlist;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class TempPlaylistDataAccessObject {
    public void writePlaylistFile(Playlist playlist) {
        String filepath = "temp_jsons/temp.json";
        JSONObject jsonObject = playlist.convertToJSON();
        try (FileWriter file = new FileWriter(filepath)) {
            file.write(jsonObject.toString());
            System.out.println("JSON object written to " + filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
