package data_access;

import entity.Playlist;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TempPlaylistDataAccessObject {
    public void writePlaylistFile(Playlist playlist) {
        String filepath = "temp_jsons/temp.json";
        JSONObject jsonObject = playlist.convertToJSON();
        try (FileWriter file = new FileWriter(filepath)) {
            file.write(jsonObject.toString());
            System.out.println("Playlist written to " + filepath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject readTempJSON(boolean print) throws IOException {
        String filePath = "temp_jsons/temp.json";
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        if (print) {
            System.out.println(jsonObject.toString(2));
        }
        return jsonObject;
    }
}
