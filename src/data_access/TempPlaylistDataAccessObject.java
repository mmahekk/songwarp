package data_access;

import entity.Playlist;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TempPlaylistDataAccessObject {
    public String file;

    public TempPlaylistDataAccessObject(String file) {
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

    public static JSONObject readTempJSON(String file, boolean print) throws IOException {
        String filePath;
        filePath = Objects.requireNonNullElse(file, "temp_jsons/temp.json");
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONObject jsonObject = new JSONObject(content);
        if (print) {
            System.out.println(jsonObject.toString(2));
        }
        return jsonObject;
    }
}
