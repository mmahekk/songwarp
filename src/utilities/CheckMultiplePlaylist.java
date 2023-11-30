package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CheckMultiplePlaylist {
    private final String filepath;
    public CheckMultiplePlaylist(String filepath) {
        this.filepath = filepath;
    }

    public boolean check() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            int openBracesCount = 0;

            int character;

            while ((character = reader.read()) != -1) {
                char currentChar = (char) character;

                if (currentChar != '\"') {
                    if (currentChar == '{') {
                        // Increment the open brace count when encountering an opening brace
                        openBracesCount++;
                    } else if (currentChar == '}') {
                        // Decrement the open brace count when encountering a closing brace
                        openBracesCount--;

                        if (openBracesCount == 0 && reader.read() != -1) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Handle the exception based on your requirements
        }
    }
}
